package edu.pdx.cs410J.nmuller;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Vector;

/**
 * This class represents a <code>PhoneBill</code>
 */
public class PhoneBill extends AbstractPhoneBill<PhoneCall> {
  private final String customer;
  public ArrayList<PhoneCall> billOfCalls = new ArrayList<>() {
  };

  /**
   * Creates a new <code>PhoneBill</code>
   * @param customer
   *        The name of the PhoneBill owner, only one per PhoneBill
   */

  public PhoneBill(String customer) {this.customer = customer;}

  /**
   *
   * Returns a <code>String</code> of the PhoneBill customer
   */
  @Override
  public String getCustomer() {
    return this.customer;
  }

  /**
   * Adds a call to billOfCalls for a specific customer bill
   * @param call
   *        Void return is successful, otherwise Exception thrown
   */
  @Override
  public void addPhoneCall(PhoneCall call) {this.billOfCalls.add(call);}

  /**
   * @return Collection in form of vector
   * returns the entire bill with all phone calls
   */
  @Override
  public Collection<PhoneCall> getPhoneCalls() {return this.billOfCalls;}

  public void sortBill(){
    Collections.sort(this.billOfCalls);
  }

}
