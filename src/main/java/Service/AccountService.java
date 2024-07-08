package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    /**
    * Checks if account username is not empty and password is at least 4 characters, then attempts to add account to database
    * @param account the account to be added to the databse
    * @return returns account with new account id if successfully added to databse, returns null otherwise
    */
    public Account addAccount(Account account) {
        if(!accountIsValid(account)) {
            return null;
        }
        return accountDAO.insertAccount(account);
    }

    /**
     * Searches database for account
     * @param account the account to search for int he database
     * @return returns account with account_id if found, returns null otherwise
     */
    public Account getAccount(Account account) {
        // early out in case username or password are not acceptable length
        if(!accountIsValid(account)) {
            return null;
        }
        return accountDAO.getAccount(account);
    }

    private boolean accountIsValid(Account account) {
        return account.getUsername() != "" && account.password.length() >= 4;
    }
}
