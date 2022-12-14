angular.module('market-front').controller('cartController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:8189/app/api/v1';

    $scope.loadCart = function () {
        $http.post('http://localhost:8189/app/api/v1/carts', $localStorage.cartName)
            .then(function (response) {
                $scope.Cart = response.data;
            });
    }

    $scope.decreaseProductFromCart = function (productId) {
        $http.put(contextPath + '/carts/' + productId, $localStorage.cartName)
            .then(function (response) {
                $scope.loadCart()
            })
    }

    $scope.removeProductFromCart = function (productId) {
        $http({
            url: contextPath + '/carts/' + productId,
            method: 'DELETE',
            data: $localStorage.cartName
        }).then(function (response) {
            $scope.loadCart()
        })
    }

    $scope.clearCart = function () {
        $http.post('http://localhost:8189/app/api/v1/carts/clear', $localStorage.cartName)
            .then(function (response) {
                $scope.loadCart();
            });
    }

    $scope.checkOut = function () {
        $http({
            url: contextPath + '/orders/' + $localStorage.cartName,
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