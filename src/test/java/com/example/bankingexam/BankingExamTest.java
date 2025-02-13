package com.example.bankingexam;

import com.example.bankingexam.exception.*;
import com.example.bankingexam.model.*;
import com.example.bankingexam.service.*;
import com.example.bankingexam.controller.*;
import com.example.bankingexam.dto.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BankingExamTest {
    private static BankAccountService bankAccountService;
    private static TransactionService transactionService;
    private static int passedTests = 0; // Contador de tests aprobados

    @BeforeAll
    public static void setup() {
        bankAccountService = new BankAccountService();
        transactionService = new TransactionService(bankAccountService); // Pasar BankAccountService
    }


    @AfterAll
    public static void calculateGrade() {
        System.out.println("\n--- Final Result ---");
        System.out.println("Tests passed: " + passedTests + " out of 50");

        int grade = (passedTests * 10) / 50;
        System.out.println("Grade achieved: " + grade + "/10");
    }


    private void incrementPassedTests() {
        passedTests++;
    }

    // Test de creación de cuentas
    @Test
    public void testCreateAccount() {
        BankAccount account = bankAccountService.createAccount("Alice", 100.0);
        assertNotNull(account);
        assertEquals("Alice", account.getAccountHolder());
        assertEquals(100.0, account.getBalance());
        incrementPassedTests();
    }

    @Test
    public void testCreateAccountWithNegativeBalance() {
        Exception exception = assertThrows(Exception.class, () -> {
            bankAccountService.createAccount("Bob", -50.0);
        });
        assertNotNull(exception);
        incrementPassedTests();
    }

    // Test de depósitos
    @Test
    public void testDeposit() {
        BankAccount account = bankAccountService.createAccount("Charlie", 100.0);
        bankAccountService.deposit(account.getId(), 50.0);
        assertEquals(150.0, account.getBalance());
        incrementPassedTests();
    }

    @Test
    public void testDepositNegativeAmount() {
        BankAccount account = bankAccountService.createAccount("Daisy", 200.0);
        Exception exception = assertThrows(Exception.class, () -> {
            bankAccountService.deposit(account.getId(), -100.0);
        });
        assertNotNull(exception);
        incrementPassedTests();
    }

    // Test de retiros
    @Test
    public void testWithdraw() throws InsufficientFundsException {
        BankAccount account = bankAccountService.createAccount("Eve", 300.0);
        bankAccountService.withdraw(account.getId(), 100.0);
        assertEquals(200.0, account.getBalance());
        incrementPassedTests();
    }

    @Test
    public void testWithdrawInsufficientFunds() {
        BankAccount account = bankAccountService.createAccount("Frank", 150.0);
        Exception exception = assertThrows(InsufficientFundsException.class, () -> {
            bankAccountService.withdraw(account.getId(), 200.0);
        });
        assertNotNull(exception);
        incrementPassedTests();
    }

    @Test
    public void testWithdrawNegativeAmount() {
        BankAccount account = bankAccountService.createAccount("Grace", 500.0);
        Exception exception = assertThrows(Exception.class, () -> {
            bankAccountService.withdraw(account.getId(), -50.0);
        });
        assertNotNull(exception);
        incrementPassedTests();
    }

    // Test de transacciones
    @Test
    public void testRecordDepositTransaction() {
        BankAccount account = bankAccountService.createAccount("Hank", 100.0);
        transactionService.recordTransaction(account.getId(), 50.0, TransactionType.DEPOSIT);
        List<Transaction> transactions = transactionService.getTransactionsForAccount(account.getId());
        assertEquals(1, transactions.size());
        assertEquals(TransactionType.DEPOSIT, transactions.get(0).getTransactionType());
        assertEquals(50.0, transactions.get(0).getAmount());
        incrementPassedTests();
    }

    @Test
    public void testRecordWithdrawalTransaction() {
        BankAccount account = bankAccountService.createAccount("Ivy", 200.0);
        transactionService.recordTransaction(account.getId(), 100.0, TransactionType.WITHDRAWAL);
        List<Transaction> transactions = transactionService.getTransactionsForAccount(account.getId());
        assertEquals(1, transactions.size());
        assertEquals(TransactionType.WITHDRAWAL, transactions.get(0).getTransactionType());
        assertEquals(100.0, transactions.get(0).getAmount());
        incrementPassedTests();
    }

    @Test
    public void testGetTransactionHistory() {
        BankAccount account = bankAccountService.createAccount("Jack", 500.0);
        transactionService.recordTransaction(account.getId(), 50.0, TransactionType.DEPOSIT);
        transactionService.recordTransaction(account.getId(), 30.0, TransactionType.WITHDRAWAL);
        List<Transaction> transactions = transactionService.getTransactionsForAccount(account.getId());
        assertEquals(2, transactions.size());
        incrementPassedTests();
    }

    @Test
    public void testEmptyTransactionHistory() {
        BankAccount account = bankAccountService.createAccount("Karen", 100.0);
        List<Transaction> transactions = transactionService.getTransactionsForAccount(account.getId());
        assertTrue(transactions.isEmpty());
        incrementPassedTests();
    }

    // Test adicionales (repetidos con variaciones)
    @Test
    public void testMultipleDeposits() {
        BankAccount account = bankAccountService.createAccount("Laura", 100.0);
        bankAccountService.deposit(account.getId(), 50.0);
        bankAccountService.deposit(account.getId(), 30.0);
        assertEquals(180.0, account.getBalance());
        incrementPassedTests();
    }

    @Test
    public void testMultipleWithdrawals() throws InsufficientFundsException {
        BankAccount account = bankAccountService.createAccount("Mike", 300.0);
        bankAccountService.withdraw(account.getId(), 100.0);
        bankAccountService.withdraw(account.getId(), 50.0);
        assertEquals(150.0, account.getBalance());
        incrementPassedTests();
    }

    // Validar creación de cuentas con nombres vacíos o nulos
    @Test
    public void testCreateAccountWithNullName() {
        Exception exception = assertThrows(Exception.class, () -> {
            bankAccountService.createAccount(null, 100.0);
        });
        assertNotNull(exception);
        incrementPassedTests();
    }

    @Test
    public void testCreateAccountWithEmptyName() {
        Exception exception = assertThrows(Exception.class, () -> {
            bankAccountService.createAccount("", 100.0);
        });
        assertNotNull(exception);
        incrementPassedTests();
    }

    // Validar depósitos en cuentas inexistentes
    @Test
    public void testDepositToNonExistentAccount() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            bankAccountService.deposit(9999L, 100.0);
        });
        assertNotNull(exception);
        incrementPassedTests();
    }

    // Validar retiros en cuentas inexistentes
    @Test
    public void testWithdrawFromNonExistentAccount() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            bankAccountService.withdraw(9999L, 50.0);
        });
        assertNotNull(exception);
        incrementPassedTests();
    }

    // Verificar que el saldo inicial de la cuenta es correcto
    @Test
    public void testInitialBalanceCheck() {
        BankAccount account = bankAccountService.createAccount("Oliver", 200.0);
        assertEquals(200.0, account.getBalance());
        incrementPassedTests();
    }

    // Verificar que el saldo no cambia tras fallar un depósito inválido
    @Test
    public void testBalanceUnchangedAfterInvalidDeposit() {
        BankAccount account = bankAccountService.createAccount("Pamela", 100.0);
        try {
            bankAccountService.deposit(account.getId(), -50.0);
        } catch (Exception ignored) {
        }
        assertEquals(100.0, account.getBalance());
        incrementPassedTests();
    }

    // Verificar que el saldo no cambia tras fallar un retiro inválido
    @Test
    public void testBalanceUnchangedAfterInvalidWithdrawal() {
        BankAccount account = bankAccountService.createAccount("Quinn", 100.0);
        try {
            bankAccountService.withdraw(account.getId(), -50.0);
        } catch (Exception ignored) {
        }
        assertEquals(100.0, account.getBalance());
        incrementPassedTests();
    }

    // Validar que el historial de transacciones aumenta correctamente
    @Test
    public void testTransactionHistorySizeAfterMultipleDeposits() {
        BankAccount account = bankAccountService.createAccount("Rachel", 100.0);
        transactionService.recordTransaction(account.getId(), 50.0, TransactionType.DEPOSIT);
        transactionService.recordTransaction(account.getId(), 30.0, TransactionType.DEPOSIT);
        List<Transaction> transactions = transactionService.getTransactionsForAccount(account.getId());
        assertEquals(2, transactions.size());
        incrementPassedTests();
    }

    // Validar tipo de transacción en el historial
    @Test
    public void testTransactionTypeRecordedCorrectly() {
        BankAccount account = bankAccountService.createAccount("Steve", 100.0);
        transactionService.recordTransaction(account.getId(), 50.0, TransactionType.DEPOSIT);
        List<Transaction> transactions = transactionService.getTransactionsForAccount(account.getId());
        assertEquals(TransactionType.DEPOSIT, transactions.get(0).getTransactionType());
        incrementPassedTests();
    }

    // Validar montos negativos en el historial de transacciones
    @Test
    public void testNegativeAmountInTransactionHistory() {
        BankAccount account = bankAccountService.createAccount("Tina", 200.0);
        try {
            transactionService.recordTransaction(account.getId(), -100.0, TransactionType.WITHDRAWAL);
        } catch (Exception ignored) {
        }
        List<Transaction> transactions = transactionService.getTransactionsForAccount(account.getId());
        assertTrue(transactions.isEmpty());
        incrementPassedTests();
    }

    // Verificar que no se permiten transacciones sin cuenta asociada
    @Test
    public void testTransactionWithoutAccount() {
        Exception exception = assertThrows(Exception.class, () -> {
            transactionService.recordTransaction(9999L, 50.0, TransactionType.DEPOSIT);
        });
        assertNotNull(exception);
        incrementPassedTests();
    }

    // Verificar múltiples depósitos y retiros en el historial
    @Test
    public void testMultipleDepositsAndWithdrawalsInHistory() {
        BankAccount account = bankAccountService.createAccount("Uma", 300.0);
        transactionService.recordTransaction(account.getId(), 50.0, TransactionType.DEPOSIT);
        transactionService.recordTransaction(account.getId(), 30.0, TransactionType.WITHDRAWAL);
        transactionService.recordTransaction(account.getId(), 70.0, TransactionType.DEPOSIT);
        List<Transaction> transactions = transactionService.getTransactionsForAccount(account.getId());
        assertEquals(3, transactions.size());
        incrementPassedTests();
    }

    // Validar que no se puede retirar más de 0 en una transacción
    @Test
    public void testWithdrawZeroAmount() {
        BankAccount account = bankAccountService.createAccount("Victor", 300.0);
        Exception exception = assertThrows(Exception.class, () -> {
            bankAccountService.withdraw(account.getId(), 0.0);
        });
        assertNotNull(exception);
        incrementPassedTests();
    }

    // Validar que no se puede depositar 0 en una transacción
    @Test
    public void testDepositZeroAmount() {
        BankAccount account = bankAccountService.createAccount("Wanda", 300.0);
        Exception exception = assertThrows(Exception.class, () -> {
            bankAccountService.deposit(account.getId(), 0.0);
        });
        assertNotNull(exception);
        incrementPassedTests();
    }

    // Validar creación de múltiples cuentas
    @Test
    public void testMultipleAccountCreation() {
        BankAccount account1 = bankAccountService.createAccount("Xander", 100.0);
        BankAccount account2 = bankAccountService.createAccount("Yvonne", 200.0);
        assertNotNull(account1);
        assertNotNull(account2);
        assertNotEquals(account1.getId(), account2.getId());
        incrementPassedTests();
    }

    // Validar que cada cuenta tiene un ID único
    @Test
    public void testUniqueAccountIds() {
        BankAccount account1 = bankAccountService.createAccount("Zack", 100.0);
        BankAccount account2 = bankAccountService.createAccount("Anna", 150.0);
        assertNotEquals(account1.getId(), account2.getId());
        incrementPassedTests();
    }

    // Validar creación de cuenta con saldo inicial cero
    @Test
    public void testCreateAccountWithZeroBalance() {
        BankAccount account = bankAccountService.createAccount("AliceZero", 0.0);
        assertNotNull(account);
        assertEquals(0.0, account.getBalance());
        incrementPassedTests();
    }

    // Validar comportamiento al intentar acceder a una cuenta inexistente
    @Test
    public void testGetNonExistentAccount() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            bankAccountService.getAccount(9999L);
        });
        assertNotNull(exception);
        incrementPassedTests();
    }

    // Validar que se puede recuperar el nombre del titular de la cuenta
    @Test
    public void testGetAccountHolderName() {
        BankAccount account = bankAccountService.createAccount("CharlieHolder", 500.0);
        assertEquals("CharlieHolder", account.getAccountHolder());
        incrementPassedTests();
    }

    // Validar transacciones múltiples en cuentas diferentes
    @Test
    public void testTransactionsOnMultipleAccounts() throws InsufficientFundsException {
        BankAccount account1 = bankAccountService.createAccount("Account1", 500.0);
        BankAccount account2 = bankAccountService.createAccount("Account2", 300.0);

        bankAccountService.deposit(account1.getId(), 50.0);
        bankAccountService.withdraw(account2.getId(), 100.0);

        assertEquals(550.0, account1.getBalance());
        assertEquals(200.0, account2.getBalance());
        incrementPassedTests();
    }

    // Validar que una cuenta no se ve afectada por las transacciones de otra cuenta
    @Test
    public void testAccountIsolation() {
        BankAccount account1 = bankAccountService.createAccount("Isolated1", 300.0);
        BankAccount account2 = bankAccountService.createAccount("Isolated2", 200.0);

        bankAccountService.deposit(account1.getId(), 50.0);

        assertEquals(350.0, account1.getBalance());
        assertEquals(200.0, account2.getBalance());
        incrementPassedTests();
    }

    // Validar cantidad de transacciones registradas en cuentas diferentes
    @Test
    public void testTransactionCountsOnMultipleAccounts() {
        BankAccount account1 = bankAccountService.createAccount("TransCount1", 100.0);
        BankAccount account2 = bankAccountService.createAccount("TransCount2", 200.0);

        transactionService.recordTransaction(account1.getId(), 50.0, TransactionType.DEPOSIT);
        transactionService.recordTransaction(account2.getId(), 30.0, TransactionType.DEPOSIT);
        transactionService.recordTransaction(account1.getId(), 20.0, TransactionType.WITHDRAWAL);

        List<Transaction> transactions1 = transactionService.getTransactionsForAccount(account1.getId());
        List<Transaction> transactions2 = transactionService.getTransactionsForAccount(account2.getId());

        assertEquals(2, transactions1.size());
        assertEquals(1, transactions2.size());
        incrementPassedTests();
    }

    // Validar que el monto en transacciones no se permite como negativo
    @Test
    public void testNegativeTransactionAmount() {
        BankAccount account = bankAccountService.createAccount("NegativeTrans", 500.0);
        Exception exception = assertThrows(Exception.class, () -> {
            transactionService.recordTransaction(account.getId(), -10.0, TransactionType.DEPOSIT);
        });
        assertNotNull(exception);
        incrementPassedTests();
    }

    // Validar manejo de excepción personalizada en retiros
    @Test
    public void testHandleInsufficientFundsException() {
        BankAccount account = bankAccountService.createAccount("InsufficientTest", 100.0);
        Exception exception = assertThrows(InsufficientFundsException.class, () -> {
            bankAccountService.withdraw(account.getId(), 200.0);
        });
        assertEquals("Insufficient balance", exception.getMessage());
        incrementPassedTests();
    }

    // Validar que una cuenta no tiene transacciones al inicio
    @Test
    public void testAccountHasNoTransactionsInitially() {
        BankAccount account = bankAccountService.createAccount("NoTransInit", 100.0);
        List<Transaction> transactions = transactionService.getTransactionsForAccount(account.getId());
        assertTrue(transactions.isEmpty());
        incrementPassedTests();
    }

    // Validar que se pueden crear múltiples transacciones en la misma cuenta
    @Test
    public void testMultipleTransactionsOnSameAccount() {
        BankAccount account = bankAccountService.createAccount("MultiTrans", 100.0);

        transactionService.recordTransaction(account.getId(), 50.0, TransactionType.DEPOSIT);
        transactionService.recordTransaction(account.getId(), 30.0, TransactionType.WITHDRAWAL);

        List<Transaction> transactions = transactionService.getTransactionsForAccount(account.getId());
        assertEquals(2, transactions.size());
        incrementPassedTests();
    }

    // Validar que no se puede crear una transacción con un monto nulo
    @Test
    public void testTransactionWithNullAmount() {
        BankAccount account = bankAccountService.createAccount("NullTrans", 100.0);
        Exception exception = assertThrows(Exception.class, () -> {
            transactionService.recordTransaction(account.getId(), 0.0, TransactionType.DEPOSIT);
        });
        assertNotNull(exception);
        incrementPassedTests();
    }

    // Validar manejo global de excepciones
    @Test
    public void testGlobalExceptionHandler() {
        BankAccount account = bankAccountService.createAccount("GlobalException", 50.0);
        Exception exception = assertThrows(InsufficientFundsException.class, () -> {
            bankAccountService.withdraw(account.getId(), 100.0);
        });
        assertEquals("Insufficient balance", exception.getMessage());
        incrementPassedTests();
    }

    // Validar que el historial refleja correctamente el saldo
    @Test
    public void testTransactionHistoryMatchesBalance() {
        BankAccount account = bankAccountService.createAccount("BalanceMatch", 200.0);
        bankAccountService.deposit(account.getId(), 50.0);
        transactionService.recordTransaction(account.getId(), 50.0, TransactionType.DEPOSIT);

        assertEquals(250.0, account.getBalance());
        incrementPassedTests();
    }

    // Validar historial vacío después de una transacción fallida
    @Test
    public void testEmptyHistoryAfterFailedTransaction() {
        BankAccount account = bankAccountService.createAccount("EmptyAfterFail", 100.0);
        try {
            transactionService.recordTransaction(account.getId(), -50.0, TransactionType.DEPOSIT);
        } catch (Exception ignored) {
        }
        List<Transaction> transactions = transactionService.getTransactionsForAccount(account.getId());
        assertTrue(transactions.isEmpty());
        incrementPassedTests();
    }

    // Validar que el monto de una transacción registrada es correcto
    @Test
    public void testTransactionAmountRecordedCorrectly() {
        BankAccount account = bankAccountService.createAccount("CorrectAmount", 100.0);
        transactionService.recordTransaction(account.getId(), 75.0, TransactionType.DEPOSIT);

        List<Transaction> transactions = transactionService.getTransactionsForAccount(account.getId());
        assertEquals(75.0, transactions.get(0).getAmount());
        incrementPassedTests();
    }

    // Validar que los IDs de transacción son únicos
    @Test
    public void testUniqueTransactionIds() {
        BankAccount account = bankAccountService.createAccount("UniqueIds", 100.0);

        transactionService.recordTransaction(account.getId(), 50.0, TransactionType.DEPOSIT);
        transactionService.recordTransaction(account.getId(), 30.0, TransactionType.WITHDRAWAL);

        List<Transaction> transactions = transactionService.getTransactionsForAccount(account.getId());
        assertNotEquals(transactions.get(0).getId(), transactions.get(1).getId());
        incrementPassedTests();
    }

    // Validar que el timestamp de una transacción es válido
    @Test
    public void testTransactionTimestamp() {
        BankAccount account = bankAccountService.createAccount("TimestampTest", 100.0);

        transactionService.recordTransaction(account.getId(), 50.0, TransactionType.DEPOSIT);
        List<Transaction> transactions = transactionService.getTransactionsForAccount(account.getId());

        assertNotNull(transactions.get(0).getTimestamp());
        incrementPassedTests();
    }

    // Validar que una transacción fallida no afecta el saldo
    @Test
    public void testBalanceUnchangedAfterFailedTransaction() {
        BankAccount account = bankAccountService.createAccount("FailedTransaction", 100.0);
        try {
            transactionService.recordTransaction(account.getId(), -50.0, TransactionType.DEPOSIT);
        } catch (Exception ignored) {
        }
        assertEquals(100.0, account.getBalance());
        incrementPassedTests();
    }

    // Validar que un retiro exacto reduce el saldo a cero
    @Test
    public void testExactWithdrawal() throws InsufficientFundsException {
        BankAccount account = bankAccountService.createAccount("ExactWithdrawal", 100.0);
        bankAccountService.withdraw(account.getId(), 100.0);
        assertEquals(0.0, account.getBalance());
        incrementPassedTests();
    }

    // Validar que las transacciones registradas se ordenan correctamente
    @Test
    public void testTransactionOrder() {
        BankAccount account = bankAccountService.createAccount("TransactionOrder", 200.0);

        // Crear transacciones
        transactionService.recordTransaction(account.getId(), 50.0, TransactionType.DEPOSIT);
        transactionService.recordTransaction(account.getId(), 30.0, TransactionType.WITHDRAWAL);

        // Obtener las transacciones
        List<Transaction> transactions = transactionService.getTransactionsForAccount(account.getId());

        // Validar que las transacciones están en orden cronológico
        assertTrue(
                transactions.get(0).getTimestamp().isBefore(transactions.get(1).getTimestamp()) ||
                        transactions.get(0).getTimestamp().isEqual(transactions.get(1).getTimestamp())
        );

        incrementPassedTests();
    }



    @Test
    public void testNewAccountHasNoTransactionsAndCorrectInitialBalance() {
        // Crear una nueva cuenta con saldo inicial
        BankAccount account = bankAccountService.createAccount("NewAccountTest", 1000.0);

        // Verificar que el saldo inicial es correcto
        assertEquals(1000.0, account.getBalance(), "El saldo inicial debe ser 1000.0");

        // Verificar que no hay transacciones asociadas a la nueva cuenta
        List<Transaction> transactions = transactionService.getTransactionsForAccount(account.getId());
        assertTrue(transactions.isEmpty(), "Una cuenta recién creada no debe tener transacciones registradas");

        // Incrementar el contador de tests aprobados
        incrementPassedTests();
    }



}
