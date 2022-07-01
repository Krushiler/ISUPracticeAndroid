package com.bravedeveloper.domain.model.user.usertypes

sealed class UsersUnion {

    class Seller(sellerType: SellerType) : UsersUnion()

    class Dispatcher(dispatcherType: DispatcherType) : UsersUnion()

    class Admin(adminType: AdminType) : UsersUnion()

    class Customer(customerType: CustomerType) : UsersUnion()
}