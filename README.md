# Bank Account Management System

A comprehensive Java-based console application for managing bank accounts with support for checking and savings accounts. This system provides essential banking operations including account creation, deposits, withdrawals, transfers, and file-based persistence.

## Features

### Core Functionality

- ✅ **Account Management**: Create, view, and delete bank accounts
- ✅ **Transaction Operations**: Deposit, withdraw, and transfer funds between accounts
- ✅ **Account Types**: Support for both Checking and Savings accounts with specialized features
- ✅ **Data Persistence**: Save and load accounts from CSV files
- ✅ **Account Number Generation**: Automatic unique 3-digit account number generation (000-999)
- ✅ **Input Validation**: Comprehensive error handling and validation

### Account Type Features

#### Checking Account

- Transaction fee of $0.50 after 5 free transactions per month
- Monthly maintenance fee of $5.00
- Automatic transaction counting and fee calculation

#### Savings Account

- Monthly interest calculation (2% annual rate, compounded monthly)
- Minimum balance requirement of $500
- Low balance fee of $10 if balance falls below minimum
- Maximum of 6 withdrawals per month
- Automatic withdrawal limit tracking

## Technologies Used

- **Java**: Core programming language
- **Object-Oriented Programming**: Classes, inheritance, polymorphism
- **File I/O**: Reading and writing CSV files
- **Data Structures**: Custom ArrayList implementation via `AccountList`
- **Collections**: HashSet for tracking used account numbers

## Project Structure

```
Bank Account Management System/
│
├── BankApp.java          # Main application entry point
├── Bank.java             # Bank class managing accounts and operations
├── Account.java          # Base Account class with core functionality
├── CheckingAccount.java  # Checking account implementation
├── SavingsAccount.java   # Savings account implementation
├── AccountList.java      # Custom list implementation for accounts
├── ListInterface.java    # Interface for list operations
├── Accounts.txt          # CSV file for account data persistence
└── README.md             # Project documentation
```

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- A Java IDE (IntelliJ IDEA, Eclipse, VS Code) or command line compiler

### Installation

1. Clone the repository:

```bash
git clone <repository-url>
cd "Bank Account Managment System"
```

2. Compile the Java files:

```bash
javac *.java
```

3. Run the application:

```bash
java BankApp
```

## Usage

### Main Menu Options

When you run the application, you'll see the following menu:

```
Options:
1. Create Account
2. Deposit
3. Withdraw
4. Transfer
5. Display All Accounts
6. Delete Accounts
7. Save Accounts
8. Load Accounts
9. Exit
```

### Creating an Account

1. Select option **1** from the menu
2. Enter the account holder's name
3. Enter the initial balance (must be non-negative)
4. Choose account type: `checking` or `savings`
5. The system will generate a unique 3-digit account number

### Making a Deposit

1. Select option **2** from the menu
2. Enter the account number
3. Enter the deposit amount (must be positive)
4. The balance will be updated automatically

### Making a Withdrawal

1. Select option **3** from the menu
2. Enter the account number
3. Enter the withdrawal amount
4. The system will validate:
   - Sufficient balance available
   - Withdrawal limits (for savings accounts)
   - Transaction fees (for checking accounts)

### Transferring Funds

1. Select option **4** from the menu
2. Enter the source account number
3. Enter the destination account number
4. Enter the transfer amount
5. Funds will be transferred between accounts

### Viewing All Accounts

1. Select option **5** from the menu
2. All accounts with their details will be displayed:
   - Account number
   - Account holder name
   - Current balance

### Deleting an Account

1. Select option **6** from the menu
2. Enter the account number to delete
3. The account will be removed from memory
4. **Important**: Use option 7 to save changes to file

### Saving Accounts to File

1. Select option **7** from the menu
2. All accounts will be saved to `Accounts.txt` in CSV format
3. Existing file will be overwritten

### Loading Accounts from File

1. Select option **8** from the menu
2. Accounts will be loaded from `Accounts.txt`
3. Existing accounts in memory will be cleared
4. Account numbers and IDs will be restored

## File Format

The `Accounts.txt` file uses CSV format:

```
AccountId, AccountHolder, Balance, AccountNumber
1,John Doe,checking,1000.0,123
2,Jane Smith,savings,5000.0,456
```

**Note**: The file format includes:

- Account ID
- Account Holder Name
- Account Type (checking/savings)
- Balance
- Account Number

## Account Number System

- Account numbers are automatically generated as 3-digit numbers (000-999)
- Each account number is unique
- The system tracks used account numbers to prevent duplicates
- Maximum of 1000 accounts supported

## Error Handling

The application includes comprehensive error handling for:

- Invalid account types
- Negative balances
- Insufficient funds
- Account not found
- Invalid input formats
- File I/O errors
- Duplicate account numbers

## Code Architecture

### Class Hierarchy

```
Account (Base Class)
├── CheckingAccount (extends Account)
└── SavingsAccount (extends Account)
```

### Key Design Patterns

- **Inheritance**: CheckingAccount and SavingsAccount extend Account
- **Polymorphism**: Different account types have specialized withdrawal behaviors
- **Encapsulation**: Private fields with public getters/setters
- **Custom Data Structure**: AccountList implements ListInterface

## Future Enhancements

Potential improvements for future versions:

- [ ] GUI implementation
- [ ] Database integration
- [ ] Transaction history tracking
- [ ] Interest calculation for checking accounts
- [ ] Multi-user support
- [ ] Password/authentication system
- [ ] Account statements
- [ ] Search and filter functionality
- [ ] Export to different file formats (JSON, XML)

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is open source and available for educational purposes.

## Author

Developed as a Java learning project demonstrating object-oriented programming concepts, file I/O operations, and data structure implementation.

## Acknowledgments

- Custom ListInterface implementation based on standard list ADT patterns
- Account number generation using Java Random and HashSet for uniqueness

---

**Note**: This is a console-based application designed for learning and demonstration purposes. For production use, additional security measures, database integration, and user interface improvements would be recommended.
