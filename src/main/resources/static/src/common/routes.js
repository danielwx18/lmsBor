lmsApp.config(["$routeProvider", function($routeProvider){
	return $routeProvider.when("/",{
		redirectTo: "/home"
	}).when("/home", {
		templateUrl: "home.html"
	}).when("/borrower", {
		templateUrl: "borrower.html"
	}).when("/borrowerservices", {
		templateUrl: "borrowerservices.html"
	}).when("/viewbranches", {
		templateUrl: "viewbranches.html"
	}).when("/viewborrowers", {
		templateUrl: "viewborrowers.html"
	}).when("/viewbooks", {
		templateUrl: "viewbooks.html"
	}).when("/viewloans", {
		templateUrl: "viewloans.html"
	})
}])