/**
 * 
 */

/**
 * Solution to StudentLoan problem
 * 
 * @author zeil
 *
 */
public class StudentLoan {

    
    
    /**
     * Compute length and total cost of a loan.
     *        
     * @param p principle of loan, in cents
     * @param interest annual interest rate
     * @param m monthly payment, in cents
     */
    private void computeLoan(long p, double interest, long m) {
        long balance = p;
        long total = 0;
        int months = 0;
        while (balance > 0) {
            double interestThisMonth = interest * (double)balance;
            long interestThisMonthL = Math.round(interestThisMonth / 12.0);
            if (interestThisMonthL >= m) {
                System.out.println("This loan will never be paid off.");
                return;
            }
            balance = balance + interestThisMonthL - m;
            ++months;
            total += m;
        }
        if (balance < 0) {
            // Correct for smaller payment in final month
            total += balance;
        }
        int years = months / 12;
        months = months % 12;
        
        long d = total / 100L;
        long c = total % 100L;
        System.out.print("The loan was paid off after " + years + " years and " + months + " months");
        System.out.print(" for a total of $" + d + ".");
        if (c < 10)
            System.out.print("0");
        System.out.println(c);
    }
