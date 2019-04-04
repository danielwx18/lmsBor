package com.gcit.lms.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookLoansDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.model.Book;
import com.gcit.lms.model.BookLoans;
import com.gcit.lms.model.Borrower;
import com.gcit.lms.model.LibraryBranch;

@RestController
@RequestMapping("/borrower")
public class BorrowerService {

	@Autowired
	BorrowerDAO brdao;

	@Autowired
	LibraryBranchDAO ldao;

	@Autowired
	BookDAO bdao;

	@Autowired
	BookLoansDAO bldao;


	@GetMapping(value="/checkBorrower",produces = "application/json")
	public Borrower checkBorrower(@RequestParam Integer cardNo) throws SQLException {
		Borrower borrower = new Borrower();
		try {
			borrower = brdao.getBorrowerByPK(cardNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return borrower;
	} 


	@GetMapping(value="/branches",produces = "application/json")
	public List<LibraryBranch> ReadAllBranch() throws SQLException {
		List<LibraryBranch> branches = new ArrayList<>();
		try {
			branches = ldao.readAllBranchesHaveBooks();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return branches;
	}


	@GetMapping(value="/branch/books",produces = "application/json")
	public List<Book> BorrowerGetBookAvailable(@RequestParam Integer branchId) throws SQLException {
		List<Book> bookIdAvailable = new ArrayList<>();
		try {
			bookIdAvailable = bdao.getAvailableBookFromBranch(branchId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bookIdAvailable;
	}
	
	@Transactional
	@PostMapping(value="/checkBookOut",produces = "application/json",consumes = "application/json")
	public boolean BorrowerBookCheckOut(@RequestBody LibraryBranch branch, Book book, Borrower borrower) throws SQLException {
		BookLoans bl = new BookLoans();

		Date out = new Date();
		int noOfDays = 7;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(out);
		calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
		Date due = calendar.getTime();

		bl.setBook(book);
		bl.setBorrower(borrower);
		bl.setLibraryBranch(branch);
		bl.setDateOut(out);
		bl.setDueDate(due);
		

		try {
			bldao.createLoan(bl);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
	@GetMapping(value="/readBranchByBorrower",produces = "application/json")
	public List<LibraryBranch> ReadReturnBranch(@RequestParam Integer cardNo)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<LibraryBranch> branches = new ArrayList<>();
		Borrower borrower = new Borrower();
		try {
			borrower = brdao.getBorrowerByPK(cardNo);
			branches = ldao.getReturnBookBranchFromBorrower(borrower);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return branches;
	}


	@GetMapping(value="/readAllBooksByBorrower",produces = "application/json")
	public List<BookLoans> ReadBorrowedBook(@RequestParam Integer cardNo) throws SQLException {
		List<BookLoans> loans = new ArrayList<>();
		Borrower borrower = new Borrower();
		try {
			borrower = brdao.getBorrowerByPK(cardNo);
			return loans;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@Transactional
	@PostMapping(value="/returnBook",produces = "application/json",consumes = "application/json")
	public boolean BookReturn(BookLoans loan, Borrower borrower, LibraryBranch branch) throws SQLException {

		Date date = new Date();

		loan.setBorrower(borrower);
		loan.setLibraryBranch(branch);
		loan.setReturnDate(date);

		try {
			bldao.updateLoan(loan);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
