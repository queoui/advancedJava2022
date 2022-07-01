package edu.pdx.cs410J.nmuller;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.Collection;

/**
 * This class represents a <code>PhoneBill</code>
 */
public class PhoneBill extends AbstractPhoneBill<PhoneCall> {
  private final String customer;

  /**
   * Creates a new <code>PhoneBill</code>
   * @param customer
   *        The name of the PhoneBill owner, only one per PhoneBill
   */

  public PhoneBill(String customer) {
    this.customer = customer;
  }

  /**
   *
   * Returns a <code>String</code> of the PhoneBill customer
   */
  @Override
  public String getCustomer() {
    return this.customer;
  }

  @Override
  public void addPhoneCall(PhoneCall call) {
    throw new UnsupportedOperationException("This method is not implemented yet");
  }

  @Override
  public Collection<PhoneCall> getPhoneCalls() {
    throw new UnsupportedOperationException("This method is not implemented yet");
  }
}
