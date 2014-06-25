package zaofond.accounts;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rinat on 19.06.14.
 */
public class Account {

    public static List<Account> accounts = new ArrayList<Account>();

    static {
        accounts.add(new Account("1", "Пензин Антон","Компания Мастер","1200","","0"));
        accounts.add(new Account("2", "Русских А.В.","ООО ЭлектроСтрой","24256","","0"));
        accounts.add(new Account("3", "Пензин Антон","Арсенал+","12600","","0"));
        accounts.add(new Account("4", "Меркин М.А.","ОАО СтальМостСтрой","400","","0"));
        accounts.add(new Account("5", "Овчаренко С.В.","Компания Север","42150","","0"));
        accounts.add(new Account("6", "Гумиров Р.И.","Компания Мега","2650","В работу","1"));
        accounts.add(new Account("7", "Ершов А.Ю.","ООО СерерРыбХоз","120000","В работу","1"));
        accounts.add(new Account("8", "Меркин М.А.","ЗАО СибНефтеМаш","20000","Зайдите","2"));
        accounts.add(new Account("9", "Пензин Антон","Компания Мастер","1200","В работу","1"));
    }

    public String COL_ID;
    public String COL_AUTHOR;
    public String COL_KONTR;
    public String COL_PRICE;
    public String COL_COMM;
    public String COL_STATUS; // 0 - нерассмотрен, 1 - одобрен, 2 - отклонен

    public Account(String COL_ID, String COL_AUTHOR, String COL_KONTR,
                    String COL_PRICE, String COL_COMM, String COL_STATUS) {
        this.COL_ID = COL_ID;
        this.COL_AUTHOR = COL_AUTHOR;
        this.COL_KONTR = COL_KONTR;
        this.COL_PRICE = COL_PRICE;
        this.COL_COMM = COL_COMM;
        this.COL_STATUS = COL_STATUS;
    }

    public void likeAccount(String comment){
        this.COL_COMM = comment;
        this.COL_STATUS = "1";
    }

    public void unlikeAccount(String comment){
        this.COL_COMM = comment;
        this.COL_STATUS = "2";
    }

    public static Account getAccountByID(int ID){
        String sID = Integer.toString(ID);
        Account sAccount = null;

        for (Account account : accounts) {
            if (account.COL_ID.equals(sID)) {
                sAccount = account;
                break;
            }
        }

        return sAccount;
    }

}
