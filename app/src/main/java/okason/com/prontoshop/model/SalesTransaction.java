package okason.com.prontoshop.model;

import android.database.Cursor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import okason.com.prontoshop.util.Constants;

/**
 * Created by Valentine on 4/25/2016.
 */
public class SalesTransaction {
    private long id;
    private long customerId;
    private double subTotalAmount;
    private double taxAmount;
    private double totalAmount;
    private boolean paid;
    private String paymentType;
    private long transactionDate;
    private long modifiedDate;


    //this property cannot be persisted
    private List<LineItem> lineItems;

    //the list of line items will be persisted into this json
    //string before it can be saved to the database
    private String jsonLineItems;

    public SalesTransaction(){}

    public SalesTransaction(long id, long customerId, double subTotalAmount, double taxAmount, double totalAmount, boolean completed, String paymentType, long transactionDate, long modifiedDate, String jsonLineItems) {
        this.id = id;
        this.customerId = customerId;
        this.subTotalAmount = subTotalAmount;
        this.taxAmount = taxAmount;
        this.totalAmount = totalAmount;
        this.paid = completed;
        this.paymentType = paymentType;
        this.transactionDate = transactionDate;
        this.modifiedDate = modifiedDate;
        this.jsonLineItems = jsonLineItems;
    }

    public static SalesTransaction getTransactionFromCursor(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndex(Constants.COLUMN_ID));
        long customerId = cursor.getLong(cursor.getColumnIndex(Constants.COLUMN_CUSTOMER_ID));
        long dateCreated = cursor.getLong(cursor.getColumnIndex(Constants.COLUMN_DATE_CREATED));
        double subTotal = cursor.getDouble(cursor.getColumnIndex(Constants.COLUMN_SUB_TOTAL_AMOUNT));
        String lineItems = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_LINE_ITEMS));
        double taxAmount = cursor.getDouble(cursor.getColumnIndex(Constants.COLUMN_TAX_AMOUNT));
        boolean completed = cursor.getInt(cursor.getColumnIndex(Constants.COLUMN_PAYMENT_STATUS)) > 0;
        String paymentType = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_PAYMENT_TYPE));
        double totalAmount = cursor.getDouble(cursor.getColumnIndex(Constants.COLUMN_TOTAL_AMOUNT));
        long dateLastUpdated = cursor.getLong(cursor.getColumnIndex(Constants.COLUMN_LAST_UPDATED));

        SalesTransaction salesTransaction = new SalesTransaction(id,customerId,subTotal,taxAmount,totalAmount,completed,paymentType,dateCreated,dateLastUpdated,lineItems);

        return salesTransaction;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public double getSubTotalAmount() {
        return subTotalAmount;
    }

    public void setSubTotalAmount(double subTotalAmount) {
        this.subTotalAmount = subTotalAmount;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public long getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(long transactionDate) {

    }

    public long getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(long modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public List<LineItem> getLineItems() {
        Gson gson = new Gson();
        String serializedLineItems = getJsonLineItems();
        List<LineItem> result = gson.<ArrayList<LineItem>>fromJson(serializedLineItems,
                new TypeToken<ArrayList<LineItem>>(){}.getType());
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        Gson gson = new Gson();
        String lineItemJson = gson.toJson(lineItems);
        this.setJsonLineItems(lineItemJson);
    }

    public String getJsonLineItems() {
        return jsonLineItems;
    }

    public void setJsonLineItems(String jsonLineItems) {
        this.jsonLineItems = jsonLineItems;
    }


}
