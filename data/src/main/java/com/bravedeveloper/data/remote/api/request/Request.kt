package com.bravedeveloper.data.remote.api.request

import com.bravedeveloper.domain.model.city.CitySuggestionsData
import com.bravedeveloper.domain.model.city.order.approvereply.ApproveReplyInput
import com.bravedeveloper.domain.model.city.order.getorder.OrderInput
import com.bravedeveloper.domain.model.city.order.ordercreate.OrderCreateInput
import com.bravedeveloper.domain.model.city.order.removeorder.RemoveOrdersInput
import com.bravedeveloper.domain.model.city.order.search.OrdersFilter
import com.bravedeveloper.domain.model.city.order.search.OrdersInput
import com.bravedeveloper.domain.model.city.order.seller.createreply.CreateReplyInput
import com.bravedeveloper.domain.model.city.order.seller.removereply.RemoveReplyInput
import com.bravedeveloper.domain.model.user.authorization.SignInInput
import com.bravedeveloper.domain.model.user.deleteownaccount.DeleteOwnAccountInput
import com.bravedeveloper.domain.model.user.notifications.NotificationsInput
import com.bravedeveloper.domain.model.user.notifications.fcm.UpsertFCMTokenInput
import com.bravedeveloper.domain.model.user.notifications.markasread.MarkAsReadInput
import com.bravedeveloper.domain.model.user.password.ChangePasswordInput
import com.bravedeveloper.domain.model.user.password.reset.ResetPasswordInput
import com.bravedeveloper.domain.model.user.rating.RateInput
import com.bravedeveloper.domain.model.user.registration.SignUpInput
import com.bravedeveloper.domain.model.user.updateprofile.UpdateProfileInput
import com.bravedeveloper.domain.model.user.verification.VerificationSmsCodeInput

object Request {

    //region Authorization

    fun signIn(input: SignInInput): String {
        return "mutation{\n" +
                "  signIn(\n" +
                "    input :{\n" +
                "      phone : \"${input.phone}\"\n" +
                "      password : \"${input.password}\"\n" +
                "    }\n" +
                "  ){\n" +
                "    token\n" +
                "    me{\n" +
                "      userId\n" +
                "      name\n" +
                "      phone\n" +
                "      role\n" +
                "      email\n" +
                "      emailVerified\n" +
                "      city{\n" +
                "        id\n" +
                "        name\n" +
                "      }\n" +
                "      avatar{\n" +
                "        id\n" +
                "        name\n" +
                "        mimeType\n" +
                "        type\n" +
                "        formats{\n" +
                "          small{\n" +
                "            url\n" +
                "            width\n" +
                "            height\n" +
                "          }\n" +
                "          medium{\n" +
                "            url\n" +
                "            width\n" +
                "            height\n" +
                "          }\n" +
                "          large{\n" +
                "            url\n" +
                "            width\n" +
                "            height\n" +
                "          }\n" +
                "        }\n" +
                "        user{\n" +
                "          __typename\n" +
                "        }\n" +
                "        createdAt\n" +
                "        updatedAt\n" +
                "      }\n" +
                "      replyPerDayLimit\n" +
                "      lastDayReplyCount\n" +
                "      unreadNotificationsCount\n" +
                "      unreadNotifications{\n" +
                "        customerNotificationsCount\n" +
                "        sellerNotificationsCount\n" +
                "      }\n" +
                "      createdAt\n" +
                "    }\n" +
                "  }\n" +
                "}"
    }

    fun signUp(input: SignUpInput): String {
        return "mutation {\n" +
                "  signUp (input: {\n" +
                "    name: \"${input.name}\"\n" +
                "    phone: \"${input.phone}\"\n" +
                "    role: ${input.role}\n" +
                "    cityId: ${input.cityId}\n" +
                "    acceptPrivacy: ${input.acceptPrivacy}\n" +
                "    shouldSendCode: ${input.shouldSendCode}\n" +
                "  }) {\n" +
                "    record {\n" +
                "      userId\n" +
                "      name\n" +
                "      phone\n" +
                "      role\n" +
                "      email\n" +
                "      emailVerified\n" +
                "      city{\n" +
                "        id\n" +
                "        name\n" +
                "      }\n" +
                "      avatar{\n" +
                "        id\n" +
                "        name\n" +
                "        mimeType\n" +
                "        type\n" +
                "        formats{\n" +
                "          small{\n" +
                "            url\n" +
                "            width\n" +
                "            height\n" +
                "          }\n" +
                "          medium{\n" +
                "            url\n" +
                "            width\n" +
                "            height\n" +
                "          }\n" +
                "          large{\n" +
                "            url\n" +
                "            width\n" +
                "            height\n" +
                "          }\n" +
                "        }\n" +
                "        user{\n" +
                "          __typename\n" +
                "        }\n" +
                "        createdAt\n" +
                "        updatedAt\n" +
                "      }\n" +
                "      replyPerDayLimit\n" +
                "      lastDayReplyCount\n" +
                "      unreadNotificationsCount\n" +
                "      unreadNotifications{\n" +
                "        customerNotificationsCount\n" +
                "        sellerNotificationsCount\n" +
                "      }\n" +
                "      createdAt\n" +
                "    }\n" +
                "    recordId\n" +
                "  }\n" +
                "}\n"
    }

    //endregion

    //region Unauthorized Actions

    fun getOrdersUnauthorized(): String {
        return "query {\n" +
                "  getOrdersUnauthorized {\n" +
                "    items {\n" +
                "      id\n" +
                "      contact\n" +
                "      destination\n" +
                "      cargoType\n" +
                "      cargoVolume\n" +
                "      measure\n" +
                "      date\n" +
                "      time\n" +
                "      number\n" +
                "      comment\n" +
                "    }\n" +
                "  }\n" +
                "}\n"
    }

    //endregion

    //region Profile Changes

    fun getMe(): String {
        return "query {\n" +
                "  me {\n" +
                "    userId\n" +
                "    name\n" +
                "    phone\n" +
                "    role\n" +
                "    email\n" +
                "    emailVerified\n" +
                "    city {\n" +
                "      id\n" +
                "      name\n" +
                "    }\n" +
                "    avatar {\n" +
                "      id\n" +
                "      name\n" +
                "      mimeType\n" +
                "      type\n" +
                "      formats {\n" +
                "        small {\n" +
                "          url\n" +
                "          width\n" +
                "          height\n" +
                "        }\n" +
                "        medium {\n" +
                "          url\n" +
                "          width\n" +
                "          height\n" +
                "        }\n" +
                "        large {\n" +
                "          url\n" +
                "          width\n" +
                "          height\n" +
                "        }\n" +
                "      }\n" +
                "      createdAt\n" +
                "      updatedAt\n" +
                "    }\n" +
                "    replyPerDayLimit\n" +
                "    lastDayReplyCount\n" +
                "    unreadNotificationsCount\n" +
                "    unreadNotifications {\n" +
                "      customerNotificationsCount\n" +
                "      sellerNotificationsCount\n" +
                "    }\n" +
                "    createdAt\n" +
                "  }\n" +
                "}"
    }

    fun updateProfile(input: UpdateProfileInput): String {
        return "mutation{\n" +
                "  updateProfile(\n" +
                "    updateProfileInput : {\n" +
                "      name : \"${input.name}\"\n" +
                "      email : \"${input.email}\"\n" +
                "      cityId : ${input.cityId}\n" +
                "    }\n" +
                "  )\n" +
                "}"
    }

    fun changePassword(input: ChangePasswordInput): String {
        return "mutation{\n" +
                "  changePassword(\n" +
                "    input : {\n" +
                "      password : \"${input.password}\"\n" +
                "      newPassword : \"${input.newPassword}\"\n" +
                "      confirmPassword : \"${input.confirmPassword}\"\n" +
                "    }\n" +
                "  )\n" +
                "}"
    }

    fun lastUpdatePassword(): String {
        return "query{\n" +
                "\tlastPasswordUpdate  \n" +
                "}\n"
    }

    fun getVerificationSmsCode(input: VerificationSmsCodeInput): String {
        return "mutation {\n" +
                "  getVerificationSmsCode(input:{action:${input.action}})\n" +
                "}"
    }

    fun deleteOwnAccount(input: DeleteOwnAccountInput): String {
        return "mutation {\n" +
                "\tdeleteOwnAccount(input:{code:${input.code}})\n" +
                "}\n"
    }

    fun resetPassword(input: ResetPasswordInput): String {
        return "mutation {\n" +
                "  resetPassword(input: { phone: \"${input.phone}\", justTimeLeft: ${input.justTimeLeft} }) {\n" +
                "    isNew\n" +
                "    timeUntilNew\n" +
                "  }\n" +
                "}\n"
    }

    fun removeAvatar(): String {
        return "mutation {\n" +
                "  removeAvatar\n" +
                "}"
    }

    fun uploadAvatar(fileName: String): String {
        return "mutation singleUPload(\$$fileName: Upload!) {\n" +
                "  uploadAvatar(file: \$$fileName) {\n" +
                "    upload {\n" +
                "      id\n" +
                "      name\n" +
                "      mimeType\n" +
                "      name\n" +
                "      type\n" +
                "      formats {\n" +
                "        small{\n" +
                "          url\n" +
                "          width\n" +
                "          height\n" +
                "        }\n" +
                "        medium{\n" +
                "          url\n" +
                "          width\n" +
                "          height\n" +
                "        }\n" +
                "        large{\n" +
                "          url\n" +
                "          width\n" +
                "          height\n" +
                "        }\n" +
                "      }\n" +
                "      createdAt\n" +
                "      updatedAt\n" +
                "    }\n" +
                "  }\n" +
                "}"
    }


    //endregion

    //region Orders

    fun getOrder(input: OrderInput): String {
        return "query {\n" +
                "  order(number: ${input.number}) {\n" +
                "    order {\n" +
                "      id\n" +
                "      contact\n" +
                "      contact\n" +
                "      phone\n" +
                "      destination\n" +
                "      coords\n" +
                "      cargoType\n" +
                "      cargoSubtype\n" +
                "      cargoVolume\n" +
                "      measure\n" +
                "      date\n" +
                "      time\n" +
                "      status\n" +
                "      price\n" +
                "      comment\n" +
                "      number\n" +
                "      customer {\n" +
                "        id\n" +
                "        name\n" +
                "        avatar {\n" +
                "          formats {\n" +
                "            small {\n" +
                "              url\n" +
                "              width\n" +
                "              height\n" +
                "            }\n" +
                "            medium {\n" +
                "              url\n" +
                "              width\n" +
                "              height\n" +
                "            }\n" +
                "            large {\n" +
                "              url\n" +
                "              width\n" +
                "              height\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "      seller {\n" +
                "        id\n" +
                "        name\n" +
                "        avatar {\n" +
                "          formats {\n" +
                "            small {\n" +
                "              url\n" +
                "              width\n" +
                "              height\n" +
                "            }\n" +
                "            medium {\n" +
                "              url\n" +
                "              width\n" +
                "              height\n" +
                "            }\n" +
                "            large {\n" +
                "              url\n" +
                "              width\n" +
                "              height\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "      replies {\n" +
                "        id\n" +
                "        price\n" +
                "        comment\n" +
                "        read\n" +
                "        approved\n" +
                "        seller{\n" +
                "          id\n" +
                "          name\n" +
                "          phone\n" +
                "          rating\n" +
                "          verified\n" +
                "          avatar{\n" +
                "            formats{\n" +
                "              small{\n" +
                "                url\n" +
                "                width\n" +
                "                height\n" +
                "              }\n" +
                "              medium{\n" +
                "                url\n" +
                "                width\n" +
                "                height\n" +
                "              }\n" +
                "              large{\n" +
                "                url\n" +
                "                width\n" +
                "                height\n" +
                "              }\n" +
                "            }\n" +
                "          }\n" +
                "          entrepreneur{\n" +
                "            active\n" +
                "          }\n" +
                "        }\n" +
                "      }" +
                "      views\n" +
                "      deletedAt\n" +
                "      createdAt\n" +
                "      updatedAt\n" +
                "    }\n" +
                "    rate{\n" +
                "      id\n" +
                "      rate\n" +
                "      completed\n" +
                "      comment\n" +
                "      orderId\n" +
                "      from{\n" +
                "        id\n" +
                "        name\n" +
                "        phone\n" +
                "        email\n" +
                "        emailVerified\n" +
                "        verified\n" +
                "        rating\n" +
                "      }\n" +
                "      to{\n" +
                "        id\n" +
                "        name\n" +
                "        phone\n" +
                "        email\n" +
                "        emailVerified\n" +
                "        verified\n" +
                "        rating\n" +
                "      }\n" +
                "      createdAt\n" +
                "      updatedAt\n" +
                "    }\n" +
                "    canRate\n" +
                "  }\n" +
                "}\n"
    }

    private fun getListString(list: List<Any>): String {
        var resultString = "["
        for (item in list) {
            resultString += "\"$item\","
        }
        resultString += "]"
        return resultString
    }

    private fun getFilterString(filter: OrdersFilter?): String {
        if (filter == null) {
            return ""
        }
        var resultString = ""
        filter.apply {
            if (userId != null) resultString += "userId:\"$userId\","
            if (role != null) resultString += "role:$role,"
            resultString += "searchType:$searchType,"
            if (materials != null) resultString += "materials:${getListString(materials!!)},"
            if (minVolume != null) resultString += "minVolume:$minVolume,"
            if (maxVolume != null) resultString += "maxVolume:$maxVolume,"
            if (cityIds != null) resultString += "cityIds:${getListString(cityIds!!)},"
        }
        return ", filter: { $resultString }"
    }

    fun getOrders(input: OrdersInput): String {
        return "query {\n" +
                "  orders(sort:${input.sort}" + getFilterString(input.filter) + ", take:${input.take}, skip:${input.skip}) {\n" +
                "    items {\n" +
                "      id\n" +
                "      contact\n" +
                "      phone\n" +
                "      destination\n" +
                "      coords\n" +
                "      cargoType\n" +
                "      cargoSubtype\n" +
                "      cargoVolume\n" +
                "      measure\n" +
                "      date\n" +
                "      time\n" +
                "      status\n" +
                "      price\n" +
                "      comment\n" +
                "      number\n" +
                "      customer {\n" +
                "        id\n" +
                "        name\n" +
                "        avatar {\n" +
                "          formats{\n" +
                "            small{\n" +
                "              url\n" +
                "              width\n" +
                "              height\n" +
                "            }\n" +
                "            medium{\n" +
                "              url\n" +
                "              width\n" +
                "              height\n" +
                "            }\n" +
                "            large{\n" +
                "              url\n" +
                "              width\n" +
                "              height\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "      seller{\n" +
                "        id\n" +
                "        name\n" +
                "        avatar{\n" +
                "          formats{\n" +
                "            small{\n" +
                "              url\n" +
                "              width\n" +
                "              height\n" +
                "            }\n" +
                "            medium{\n" +
                "              url\n" +
                "              width\n" +
                "              height\n" +
                "            }\n" +
                "            large{\n" +
                "              url\n" +
                "              width\n" +
                "              height\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "      replies{\n" +
                "        id\n" +
                "        price\n" +
                "        comment\n" +
                "        read\n" +
                "      }\n" +
                "      views\n" +
                "      deletedAt\n" +
                "      createdAt\n" +
                "      updatedAt\n" +
                "    }\n" +
                "    pageInfo {\n" +
                "      page\n" +
                "      perPage\n" +
                "      totalPages\n" +
                "      totalItems\n" +
                "      hasPreviousPage\n" +
                "      hasNextPage\n" +
                "    }\n" +
                "  }\n" +
                "}\n"
    }

    fun createReply(input: CreateReplyInput): String {
        return "mutation{\n" +
                "\tcreateReply(input:{\n" +
                "    orderId:\"${input.orderId}\"\n" +
                "    price:${input.price}\n" +
                "    comment:\"${input.comment}\"\n" +
                "  }){\n" +
                "    recordId\n" +
                "    record{\n" +
                "      id\n" +
                "      contact\n" +
                "      phone\n" +
                "      destination\n" +
                "      coords\n" +
                "      cargoType\n" +
                "      cargoSubtype\n" +
                "      cargoVolume\n" +
                "      measure\n" +
                "      date\n" +
                "      time\n" +
                "      status\n" +
                "      price\n" +
                "      comment\n" +
                "      number\n" +
                "      customer {\n" +
                "        id\n" +
                "        name\n" +
                "        avatar {\n" +
                "          formats {\n" +
                "            small {\n" +
                "              url\n" +
                "              width\n" +
                "              height\n" +
                "            }\n" +
                "            medium {\n" +
                "              url\n" +
                "              width\n" +
                "              height\n" +
                "            }\n" +
                "            large {\n" +
                "              url\n" +
                "              width\n" +
                "              height\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "      seller {\n" +
                "        id\n" +
                "        name\n" +
                "        avatar {\n" +
                "          formats {\n" +
                "            small {\n" +
                "              url\n" +
                "              width\n" +
                "              height\n" +
                "            }\n" +
                "            medium {\n" +
                "              url\n" +
                "              width\n" +
                "              height\n" +
                "            }\n" +
                "            large {\n" +
                "              url\n" +
                "              width\n" +
                "              height\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "      replies {\n" +
                "        id\n" +
                "        price\n" +
                "        comment\n" +
                "        read\n" +
                "        approved\n" +
                "        seller{\n" +
                "          name\n" +
                "          phone\n" +
                "          rating\n" +
                "          verified\n" +
                "          avatar{\n" +
                "            formats{\n" +
                "              small{\n" +
                "                url\n" +
                "                width\n" +
                "                height\n" +
                "              }\n" +
                "              medium{\n" +
                "                url\n" +
                "                width\n" +
                "                height\n" +
                "              }\n" +
                "              large{\n" +
                "                url\n" +
                "                width\n" +
                "                height\n" +
                "              }\n" +
                "            }\n" +
                "          }\n" +
                "          entrepreneur{\n" +
                "            active\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "      views\n" +
                "      deletedAt\n" +
                "      createdAt\n" +
                "      updatedAt\n" +
                "    }\n" +
                "  }\n" +
                "}"
    }

    fun getCargo(): String {
        return "query{\n" +
                "  cargo {\n" +
                "    cargo{\n" +
                "      id\n" +
                "      type\n" +
                "      positionNumber\n" +
                "      subtypes{\n" +
                "        id\n" +
                "        subtype\n" +
                "      }\n" +
                "    }\n" +
                "    measures{\n" +
                "      id\n" +
                "      name\n" +
                "    }\n" +
                "  }\n" +
                "}"
    }

    fun orderCreate(input: OrderCreateInput): String {
        return "mutation{\n" +
                "  orderCreate(input:{\n" +
                "    coords:[${input.coords.first}, ${input.coords.second}],\n" +
                "    contact:\"${input.contact}\",\n" +
                "    phone:\"${input.phone}\",\n" +
                "    destination:\"${input.destination}\",\n" +
                "    cargoType:\"${input.cargoType}\",\n" +
                "    cargoSubtype:\"${input.cargoSubtype}\",\n" +
                "    cargoVolume:${input.cargoVolume},\n" +
                "    date:\"${input.date}\",\n" +
                "    time:${input.time},\n" +
                "    comment:\"${input.comment}\",\n" +
                "    measure:\"${input.measure}\"\n" +
                "  }) {\n" +
                "    recordId\n" +
                "    record{\n" +
                "      number\n" +
                "    }\n" +
                "  }\n" +
                "}"
    }

    fun getNotifications(input: NotificationsInput): String {
        return "query {\n" +
                "  replies(\n" +
                "    sort: { sortBy: ${input.sort} }\n" +
                "    filter: { role: ${input.filter} }\n" +
                "    skip: ${input.skip}\n" +
                "    take: ${input.take}\n" +
                "  ) {\n" +
                "    items {\n" +
                "      latest {\n" +
                "        id\n" +
                "        price\n" +
                "        comment\n" +
                "        read\n" +
                "        approved\n" +
                "        order {\n" +
                "          id\n" +
                "          status\n" +
                "          measure\n" +
                "          cargoType\n" +
                "          cargoSubtype\n" +
                "          cargoVolume\n" +
                "          date\n" +
                "          time\n" +
                "          customer {\n" +
                "            id\n" +
                "            name\n" +
                "          }\n" +
                "          destination\n" +
                "          comment\n" +
                "          replies {\n" +
                "            id\n" +
                "            seller {\n" +
                "              id\n" +
                "            }\n" +
                "            comment\n" +
                "            price\n" +
                "            approved\n" +
                "          }\n" +
                "          phone\n" +
                "        }\n" +
                "        seller {\n" +
                "          name\n" +
                "          avatar {\n" +
                "            formats {\n" +
                "              small {\n" +
                "                url\n" +
                "              }\n" +
                "              medium {\n" +
                "                url\n" +
                "              }\n" +
                "              large {\n" +
                "                url\n" +
                "              }\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "        price\n" +
                "        createdAt\n" +
                "        updatedAt\n" +
                "        deletedAt\n" +
                "      }\n" +
                "      readed {\n" +
                "        id\n" +
                "        price\n" +
                "        comment\n" +
                "        read\n" +
                "        approved\n" +
                "        order {\n" +
                "          id\n" +
                "          status\n" +
                "          measure\n" +
                "          cargoType\n" +
                "          cargoSubtype\n" +
                "          cargoVolume\n" +
                "          date\n" +
                "          time\n" +
                "          customer {\n" +
                "            id\n" +
                "            name\n" +
                "          }\n" +
                "          destination\n" +
                "          comment\n" +
                "          replies {\n" +
                "            id\n" +
                "            seller {\n" +
                "              id\n" +
                "            }\n" +
                "            comment\n" +
                "            price\n" +
                "            approved\n" +
                "          }\n" +
                "          phone\n" +
                "        }\n" +
                "        seller {\n" +
                "          name\n" +
                "          avatar {\n" +
                "            formats {\n" +
                "              small {\n" +
                "                url\n" +
                "              }\n" +
                "              medium {\n" +
                "                url\n" +
                "              }\n" +
                "              large {\n" +
                "                url\n" +
                "              }\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "        price\n" +
                "        createdAt\n" +
                "        updatedAt\n" +
                "        deletedAt\n" +
                "      }\n" +
                "    }\n" +
                "    pageInfo {\n" +
                "      totalPages\n" +
                "      totalItems\n" +
                "      page\n" +
                "      perPage\n" +
                "      hasNextPage\n" +
                "      hasPreviousPage\n" +
                "    }\n" +
                "  }\n" +
                "}\n"
    }

    fun markRepliesAsRead(input: MarkAsReadInput): String {
        return "mutation{\n" +
                "  markRead(\n" +
                "    ids: ${getListString(input.ids)}\n" +
                "  )\n" +
                "}"
    }

    fun removeReply(input: RemoveReplyInput): String {
        return "mutation {\n" +
                "  removeReply(input: { orderId: \"${input.orderId}\", replyId: \"${input.replyId}\" }) {\n" +
                "    recordId\n" +
                "    record {\n" +
                "      id\n" +
                "      contact\n" +
                "      phone\n" +
                "      destination\n" +
                "      coords\n" +
                "      cargoType\n" +
                "      cargoSubtype\n" +
                "      cargoVolume\n" +
                "      measure\n" +
                "      date\n" +
                "      time\n" +
                "      status\n" +
                "      price\n" +
                "      comment\n" +
                "      number\n" +
                "      customer {\n" +
                "        id\n" +
                "        name\n" +
                "        avatar {\n" +
                "          formats {\n" +
                "            small {\n" +
                "              url\n" +
                "              width\n" +
                "              height\n" +
                "            }\n" +
                "            medium {\n" +
                "              url\n" +
                "              width\n" +
                "              height\n" +
                "            }\n" +
                "            large {\n" +
                "              url\n" +
                "              width\n" +
                "              height\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "      seller {\n" +
                "        id\n" +
                "        name\n" +
                "        avatar {\n" +
                "          formats {\n" +
                "            small {\n" +
                "              url\n" +
                "              width\n" +
                "              height\n" +
                "            }\n" +
                "            medium {\n" +
                "              url\n" +
                "              width\n" +
                "              height\n" +
                "            }\n" +
                "            large {\n" +
                "              url\n" +
                "              width\n" +
                "              height\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "      replies {\n" +
                "        id\n" +
                "        price\n" +
                "        comment\n" +
                "        read\n" +
                "        approved\n" +
                "        seller {\n" +
                "          id\n" +
                "          name\n" +
                "          phone\n" +
                "          rating\n" +
                "          verified\n" +
                "          avatar {\n" +
                "            formats {\n" +
                "              small {\n" +
                "                url\n" +
                "                width\n" +
                "                height\n" +
                "              }\n" +
                "              medium {\n" +
                "                url\n" +
                "                width\n" +
                "                height\n" +
                "              }\n" +
                "              large {\n" +
                "                url\n" +
                "                width\n" +
                "                height\n" +
                "              }\n" +
                "            }\n" +
                "          }\n" +
                "          entrepreneur {\n" +
                "            active\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "      views\n" +
                "      deletedAt\n" +
                "      createdAt\n" +
                "      updatedAt\n" +
                "    }\n" +
                "  }\n" +
                "}\n"
    }

    fun removeOrders(input: RemoveOrdersInput): String {
        return "mutation{\n" +
                "  removeOrders(input:{\n" +
                "    ids:${getListString(input.ids)}\n" +
                "  })\n" +
                "}"
    }

    fun approveReply(input: ApproveReplyInput): String {
        return "mutation{\n" +
                "  approveReply(input:{\n" +
                "    orderId:\"${input.orderId}\",\n" +
                "    replyId:\"${input.replyId}\"\n" +
                "  }) {\n" +
                "   \trecordId,\n" +
                "    record{\n" +
                "      id,\n" +
                "      number\n" +
                "    }\n" +
                "  }\n" +
                "} "
    }

    fun upsertFCMToken(input: UpsertFCMTokenInput): String {
        return "mutation {\n" +
                "  upsertFcmToken(token:\"${input.token}\")\n" +
                "}"
    }

    fun getCitiesBySubstring(input: CitySuggestionsData): String {
        return "query {\n" +
                "  cities(\n" +
                "    filter:{\n" +
                "      name:\"${input.substring}\"\n" +
                "    }\n" +
                "    take:${input.take}\n" +
                "  ) {\n" +
                "    items {\n" +
                "      name\n" +
                "    }\n" +
                "  }\n" +
                "}"
    }

    fun rateUser(input: RateInput): String {
        return "mutation {\n" +
                "  rate(input:{\n" +
                "    rate:${input.rate},\n" +
                "    completed:${input.completed},\n" +
                "    comment:\"${input.comment}\",\n" +
                "    userId:\"${input.userId}\",\n" +
                "    orderId:\"${input.orderId}\"\n" +
                "  }) {\n" +
                "    rateId\n" +
                "  }\n" +
                "}"
    }

    //endregion
}