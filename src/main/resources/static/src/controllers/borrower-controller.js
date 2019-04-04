lmsApp.controller("borrowerController", function($scope, $http, $window,
		lmsFactory, lmsConstants, Pagination) {
	lmsFactory.getAll(
			lmsConstants.BORROWER_SERVER_URL + lmsConstants.GET_ALL_BRANCHES).then(
			function(data) {
				$scope.mybranchs = data;
				$scope.pagination = Pagination.getNew(5);
				$scope.pagination.numPages = Math.ceil($scope.mybranchs.length
						/ $scope.pagination.perPage);
			})

	$scope.checkCardNo = function(cardNo) {
		lmsFactory.getAll(
				lmsConstants.BORROWER_SERVER_URL + lmsConstants.GET_VALID_BORROWER+cardNo).then(
				function(data) {
					if( data.cardNo != null) {
						lmsFactory.getAll(
							lmsConstants.BORROWER_SERVER_URL
									+ lmsConstants.GET_ALL_BRANCHES).then(
							function(data) {
								$scope.mybranchs = data;
							})
							$window.location = "#/viewbranches"
					} else {
						alert("Enter our card no. again!");
					}
					
						
				})
	}

		
	$scope.viewBooks = function(branch) {
		lmsFactory.getAll(
				lmsConstants.BORROWER_SERVER_URL + lmsConstants.GET_ALL_BOOKS+branch.branchId).then(
				function(data) {
					$scope.theBooks = data;
					$scope.pagination = Pagination.getNew(5);
					$scope.pagination.numPages = Math.ceil($scope.mybranchs.length
							/ $scope.pagination.perPage);
				})
				$window.location = "#/viewbooks"
	}
	
	$scope.enterBranchModal = function(branch) {
		$scope.branch = branch;
	}
	
	$scope.updateBorrower = function(branch) {
		lmsFactory.saveObject(
				lmsConstants.BORROWER_SERVER_URL + lmsConstants.UPDATE_BRANCH,
				branch).then(
				function() {
					lmsFactory.getAll(
							lmsConstants.BORROWER_SERVER_URL
									+ lmsConstants.GET_ALL_BRANCHES).then(
							function(data) {
								$scope.mybranchs = data;
							})
					$window.location = "#/viewbranches"
				})
	}

	$scope.cancel = function() {
		$window.location = "#/viewbranches"
	}

	$scope.searchBranch = function() {
		lmsFactory.getAll(
				lmsConstants.BORROWER_SERVER_URL + lmsConstants.GET_ALL_BRANCHES
						+ $scope.searchString.toLowerCase()).then(
				function(data) {
					$scope.mybranchs = data;
					$scope.pagination = Pagination.getNew(5);
					$scope.pagination.numPages = Math
							.ceil($scope.mybranchs.length
									/ $scope.pagination.perPage);
				})
	}

	

})