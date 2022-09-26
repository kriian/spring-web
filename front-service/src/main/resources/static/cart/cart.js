angular.module('market-front').controller('cartController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:5555/cart/api/v1/carts';

    $scope.loadCart = function () {
        $http.post(contextPath, $localStorage.cartName)
            .then(function (response) {
                $scope.Cart = response.data;
            });
    }

    $scope.decreaseProductFromCart = function (productId) {
        $http({
            url: contextPath +'/' + productId,
            method: 'PUT',
            data: $localStorage.cartName
        }).then(function (response) {
                $scope.loadCart()
            })
    }

    $scope.removeProductFromCart = function (productId) {
        $http({
            url: contextPath + '/' + productId,
            method: 'DELETE',
            data: $localStorage.cartName
        }).then(function (response) {
            $scope.loadCart()
        })
    }

    $scope.clearCart = function () {
        $http.post(contextPath + '/clear', $localStorage.cartName)
            .then(function (response) {
                $scope.loadCart();
            });
    }

    $scope.checkOut = function () {
        $http({
            url: 'http://localhost:5555/gateway/api/v1/orders/' + $localStorage.cartName,
            method: 'POST',
            data: $scope.orderDetails
        }).then(function (response) {
                $scope.loadCart();
                $scope.orderDetails = null
            });
    };

    $scope.disabledCheckOut = function () {
        alert("Для оформления заказа необходимо войти в учетную запись");
    }

    $scope.loadCart();

});