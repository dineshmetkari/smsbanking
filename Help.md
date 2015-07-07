# Help #

## Initial set-up ##

There are few steps to complete before you actually can use the application:
  1. Specify your SMS Banking PIN code
  1. Configure SMS Banking Phone Number (also referred to as "Teller Number"
  1. Create at least one subscription (sub hereafter)

In fact, user is prompted to complete all these steps using a wizard when the application is first launched. The application will also provide some related instructions.

![http://smsbanking.googlecode.com/svn/img/firstl.png](http://smsbanking.googlecode.com/svn/img/firstl.png)

Sections below, however, describe these steps in more details.



### Configure Teller Number ###
Teller Number is the Phone Number messages will be sent to. You should be provided with the one by your mobile career or your bank. Sure enough this number should be known to the application.
How to access this set-up option: from the list open the standard menu (with menu button) and tap "Edit Teller Number" menu item.

![http://smsbanking.googlecode.com/svn/img/menu.png](http://smsbanking.googlecode.com/svn/img/menu.png)



### Configure PIN code ###
PIN code is the PIN code issued to you by your bank to access the SMS Banking service. Please note that this is NOT your credit card PIN code.
PIN code is stored securely in the application. So once entered, it can be modified, but normally can't be viewed by anyone including you.
PIN code is automatically inserted to the messages template when sending the message.

How to access this set-up option: same as for "Edit Teller Number" feature, from the list open the standard menu (with menu button) and tap "Edit Teller Number" menu item. See screenshot above.

### Create a subscription ###
Subscription is a template of the message which is to be sent to Teller Number. Content of the message is usually provided to you by bank. In most cases the message consists of: an instruction (check balance, pay for smth.), a parameter (like an amount to be withdrawn for example) and a


## Settings ##
TODO

# About #

## What is SMS Banking? ##

SMS Banking is a feature available in certain banks: users are allowed to send simple structured text messages to trigger certain actions with their bank account. For example:

  1. SMS "BAL 1234" is sent to certain phone number (bank's one)
> 2. This is an instructoin to your bank - "Send me my balance. My SMS Banking PIN code is 1234".
> 3. Your bank sends you an SMS "OK:BALANCE=987".
> 4. Which you understand as "Your message was processed all right, and you current balance is 987". You should probabaly know the currency of your account... :)

## What's in it for me? ##

Android SMS Banking Helper (shortly, "SMS Bank") is basically an SMS templating application, and a tool to automatically process the SMS you get in a reply. However, unlike regular SMS templating applicatios is also enables you with the followinng features:

  * Enhanced secutiry. Nobody can access your SMS Banking PIN code, or find out your list of SMS Banking subscriptions (aka action codes, like "BAL" from the example above). PIN code and templates are entered once only, and are stored securely.
  * Easy access to SMS Bankign features. Once configured, you can trigger actions with just a single tap on a screen, and receive the reply in a human-readable form at once.