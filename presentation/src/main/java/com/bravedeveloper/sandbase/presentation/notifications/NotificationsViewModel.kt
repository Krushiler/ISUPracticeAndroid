package com.bravedeveloper.sandbase.presentation.notifications

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bravedeveloper.domain.model.city.order.OrderStatusEnum
import com.bravedeveloper.domain.model.city.order.approvereply.ApproveReplyInput
import com.bravedeveloper.domain.model.user.notifications.NotificationsInput
import com.bravedeveloper.domain.model.user.notifications.RepliesSortEnum
import com.bravedeveloper.domain.model.user.notifications.Reply
import com.bravedeveloper.domain.model.user.notifications.markasread.MarkAsReadInput
import com.bravedeveloper.domain.usecase.notifications.GetNotificationsUseCase
import com.bravedeveloper.domain.usecase.notifications.MarkAsReadUseCase
import com.bravedeveloper.domain.usecase.orders.customer.ApproveReplySingleUseCase
import com.bravedeveloper.sandbase.presentation.base.BaseAndroidViewModel
import com.bravedeveloper.sandbase.presentation.base.util.getOrderFullTime
import com.bravedeveloper.sandbase.presentation.base.util.getTimesAgoString
import com.bravedeveloper.sandbase.presentation.base.util.getTitle
import com.bravedeveloper.sandbase.presentation.base.util.getVolumeAndMeasure
import com.bravedeveloper.sandbase.presentation.global.User
import com.bravedeveloper.sandbase.presentation.navigation.Screens
import com.bravedeveloper.sandbase.presentation.notifications.affordedOrder.AffordedOrderItem
import com.bravedeveloper.sandbase.presentation.notifications.buyerOrder.NewNotifItem
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val user: User,
    private val getNotificationsUseCase: GetNotificationsUseCase,
    private val markAsReadUseCase: MarkAsReadUseCase,
    private val approveReplyUseCase: ApproveReplySingleUseCase,
    private val router: Router,
    application: Application
) : BaseAndroidViewModel(application) {

    private val takeRepliesCount: Int = 5
    private var skipRepliesCount: Int = 0

    private val repliesLiveData = MutableLiveData<List<NotificationItem>>()
    private val hasUnreadRepliesLiveData = MutableLiveData(false)
    private var hasMoreReplies = false

    fun getReplies() {
        updateInput()
        repliesLiveData.value = listOf()
        loadRepliesWithSavedFilter()
    }

    private fun updateInput() {
        val userRole = user.user?.role
        if (userRole != null) {
            getNotificationsUseCase.saveInput(
                NotificationsInput(
                    filter = userRole,
                    sort = RepliesSortEnum.CREATEDAT_DESC,
                    take = takeRepliesCount,
                    skip = skipRepliesCount
                )
            )
        }
    }

    private fun loadRepliesWithSavedFilter() {
        getNotificationsUseCase.execute()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                val newReplies: MutableList<NotificationItem> =
                    if (repliesLiveData.value != null) repliesLiveData.value!!.toMutableList() else mutableListOf()
                hasMoreReplies = it.data?.replies?.pageInfo?.hasNextPage == true
                it.data?.replies?.items?.readed?.let { replies -> newReplies.addAll(replies.map { reply -> reply.toNotificationItem() }) }
                it.data?.replies?.items?.latest?.let { replies -> newReplies.addAll(replies.map { reply -> reply.toNotificationItem() }) }

                var hasUnreadReplies = false

                if (!it.data?.replies?.items?.latest.isNullOrEmpty()) {
                    hasUnreadReplies = true
                }
                hasUnreadRepliesLiveData.value = hasUnreadReplies
                repliesLiveData.value = newReplies
            }, {
                Log.e(it::class.simpleName, it.message.toString())
            })?.untilCleared()
    }

    fun loadMore() {
        if (hasMoreReplies) {
            skipRepliesCount += takeRepliesCount
            updateInput()
            loadRepliesWithSavedFilter()
        }
    }

    fun reloadReplies() {
        skipRepliesCount = 0
        updateInput()
        repliesLiveData.value = listOf()
        loadRepliesWithSavedFilter()
    }

    fun getRepliesLiveData(): LiveData<List<NotificationItem>> = repliesLiveData

    fun getHasUnreadRepliesLiveData(): LiveData<Boolean> = hasUnreadRepliesLiveData

    private fun Reply.toNotificationItem(): NotificationItem {
        if (order?.customer?.id == user.user?.userId && order?.status == OrderStatusEnum.NEW) {
            return NewNotifItem(
                replyId = id.orEmpty(),
                orderId = order?.id.orEmpty(),
                orderNumber = order?.number,
                title = order?.getTitle(getApplication<Application>().resources).orEmpty(),
                weight = order?.getVolumeAndMeasure(getApplication<Application>().resources)
                    .orEmpty(),
                sellerName = seller?.name.orEmpty(),
                sellerComment = comment.orEmpty(),
                sellerRating = seller?.rating.orZero(),
                approved = seller?.verified == true,
                money = true,
                sertificated = seller?.entrepreneur?.active == true,
                price = price.orZero().toString(),
                timeAgo = if (createdAt != null) getTimesAgoString(
                    createdAt!!,
                    getApplication<Application>().resources
                ) else ""
            )
        } else {
            return AffordedOrderItem(
                geolocation = order?.destination.orEmpty(),
                replyId = id.orEmpty(),
                orderId = order?.id.orEmpty(),
                orderNumber = order?.number,
                weight = order?.getTitle(getApplication<Application>().resources).orEmpty(),
                price = price.orZero().toString(),
                timeAgo = if (createdAt != null) getTimesAgoString(
                    createdAt!!,
                    getApplication<Application>().resources
                ) else "",
                buyerName = order?.customer?.name.orEmpty(),
                buyerComment = comment.orEmpty(),
                orderComment = order?.comment.orEmpty(),
                phone = order?.phone.orEmpty(),
                dateAndTime = if (order?.time != null && order?.date != null)
                    getOrderFullTime(
                        order?.date.toString(),
                        order?.time!!,
                        getApplication<Application>().resources
                    ) else "",
            )
        }
    }

    private fun Int?.orZero(): Int {
        if (this != null) {
            return this
        }
        return 0
    }

    fun markAllAsRead(notificationItems: List<NotificationItem>) {
        markAsReadUseCase.saveInput(MarkAsReadInput(notificationItems.map { it.replyId }))
        markAsReadUseCase.execute()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
//                        Response Received
            }, {
                Log.e(it::class.simpleName, it.message.toString())
            })?.untilCleared()
    }

    fun goToOrderInfoScreen() {
        router.navigateTo(Screens.orderInfoScreen())
    }

    fun approveReply(orderId: String, replyId: String) {
        approveReplyUseCase.saveInput(ApproveReplyInput(orderId, replyId))
        approveReplyUseCase.execute()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                reloadReplies()
            }, {
                Log.e(it::class.simpleName, it.message.toString())
            })?.untilCleared()
    }

}