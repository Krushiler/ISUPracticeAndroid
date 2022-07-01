package com.bravedeveloper.domain.model.user.usertypes

sealed class ClientsUnion {

    class Seller(sellerType: SellerType) : ClientsUnion()

    class Dispatcher(dispatcherType: DispatcherType) : ClientsUnion()

    class Customer(customerType: CustomerType) : ClientsUnion()
}