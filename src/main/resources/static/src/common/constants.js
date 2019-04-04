lmsApp.constant("lmsConstants",{
	ADMIN_SERVER_URL: "http://localhost:8060/lms",
	BORROWER_SERVER_URL: "http://localhost:8070/lms",
	LIBRARIAN_SERVER_URL: "http://localhost:8080/lms",
	GET_ALL_BORROWERS:"/borrower/readAllBorrowersByAd?name=",
	UPDATE_BORROWER:"/borrower/updateBorrowers",
	GET_ALL_BRANCHES:"/borrower/branches",
	UPDATE_BRANCH:"/borrower/updateLibraryBranches",
	GET_ALL_BOOKS:"/borrower/branch/books?branchId=",
	UPDATE_BOOK:"/borrower/updateBook",
	GET_ALL_LOANS:"/borrower/readAllLoansWithDueDate?name=",
	GET_VALID_BORROWER:"/borrower/checkBorrower?cardNo="
})