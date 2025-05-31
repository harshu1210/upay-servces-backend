package com.service.upay_services_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.service.upay_services_service.enitites.Dealers;
import com.service.upay_services_service.enitites.User;

@Service
public class userEmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendRegistrationEmail(String to, String name, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Welcome to Upay Services - Your Account Details");
        message.setText("Hello " + name + ",\n\n"
                + "Your account has been created successfully.\n"
                + "Here are your login credentials:\n\n"
                + "Email: " + to + "\n"
                + "Password: " + password + "\n\n"
                + "Please change your password after first login.\n\n"
                + "Regards,\nUpay Services Team");

        mailSender.send(message);
    }

    public void sendUpdationUser(User oldUser, User newUser ) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(oldUser.getEmail());
        message.setSubject("User Details Updated – ["+oldUser.getFullName()+"]");
        message.setText("Hi User,\n" + //
                        "\n" + //
                        "Please note that the details for the following User have been successfully updated in our system.\n" + //
                        "\n" + //
                        "🔄 **Updated User Information:**\n" + //
                        "- **Active:** ["+newUser.isActive()+"]\n" + //
                        "- **Role:** ["+newUser.getRole()+"]\n" + //
                        "\n" + //
                        "🔄 **Old User Information:**\n" + //
                        "- **Active:** ["+newUser.isActive()+"]\n" + //
                        "- **Role:** ["+newUser.getRole()+"]\n");

        mailSender.send(message);
    }

    public void sendDeletionUser(User user ) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail(), upay_team);
        message.setSubject("User Details Deleted – ["+user.getFullName()+"]");
        message.setText("Hi Team,\n" + //
                        "\n" + //
                        "Please note that the details for the following User have been successfully deleted in our system.\n"
        );

        mailSender.send(message);
    }

    public void sendRegistrationService(String service) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(upay_team);
        message.setSubject("Registered New Vendor Service");
        message.setText("Hi Team,\n" + //
                        "\n" + //
                        "We’ve successfully integrated a new vendor service: **"+service+"** into our platform.\n"
                        );

        mailSender.send(message);
    }

    public void sendRegistrationDealer(Dealers dealer) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(upay_team);
        message.setSubject("Registered New Dealer: "+dealer.getCompanyName());
        message.setText("Hi Team,\n" + //
                        "\n" + //
                        "We Welcome "+dealer.getCompanyName()+ " into our platform.\n" +
                        "Company Name: "+dealer.getCompanyName() +"\n"+
                        "Business Email: "+dealer.getBusinessEmail() +"\n"+
                        "Business Phone Number: "+dealer.getBusinessPhoneNumber() +"\n"
                        );

        mailSender.send(message);
    }

    public void sendUpdationService(String newService, String oldService) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(upay_team);
        message.setSubject("Service Name Update Notification: ["+newService+"] → ["+oldService+"]");
        message.setText("Hi Team,\n" + //
                        "\n" + //
                        "Please be informed that the service previously known as **\"["+oldService+"]\"** has been renamed to **\"["+newService+"]\"** as part of our recent update.\n" + //
                        "\n" + 
                        "This change is reflected across all relevant systems and documentation. Please update any references to the service accordingly.\n");

        mailSender.send(message);
    }

    public void sendUpdationDealer(Dealers newDealer, Dealers oldDealer) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(upay_team);
        message.setSubject("Dealer Details Updated – ["+oldDealer.getCompanyName()+"]");
        message.setText("Hi Team,\n" + //
                        "\n" + //
                        "Please note that the details for the following dealer have been successfully updated in our system.\n" + //
                        "\n" + //
                        "🔄 **Updated Dealer Information:**\n" + //
                        "- **Dealer Name:** ["+newDealer.getCompanyName()+"]\n" + //
                        "- **Dealer ID / Code:** ["+newDealer.getId()+"]\n" + //
                        "- **Business Email:** ["+newDealer.getBusinessEmail()+"]\n" + //
                        "- **Business Phone Number:** ["+newDealer.getBusinessPhoneNumber()+"]\n" + //
                        "- **GST Number:** ["+newDealer.getGstNumber()+"]\n" + //
                        "- **Beneficiary Name:** ["+newDealer.getBankDetails().getBeneficiaryName()+"]\n" + //
                        "- **Bank Name:** ["+newDealer.getBankDetails().getBankName()+"]\n" + //
                        "- **Branch Name:** ["+newDealer.getBankDetails().getBranchName()+"]\n" + //
                        "- **Account Number:** ["+newDealer.getBankDetails().getAccountNumber()+"]\n" + //
                        "- **IFSC Code:** ["+newDealer.getBankDetails().getIfsccode()+"]\n" + //
                        "- **UPI ID:** ["+newDealer.getBankDetails().getUpiid()+"]\n" + //
                        "\n" + //
                        "🔄 **Old Dealer Information:**\n" + //
                        "- **Dealer Name:** ["+oldDealer.getCompanyName()+"]\n" + //
                        "- **Dealer ID / Code:** ["+oldDealer.getId()+"]\n" + //
                        "- **Business Email:** ["+oldDealer.getBusinessEmail()+"]\n" + //
                        "- **Business Phone Number:** ["+oldDealer.getBusinessPhoneNumber()+"]\n" + //
                        "- **GST Number:** ["+oldDealer.getGstNumber()+"]\n" + //
                        "- **Beneficiary Name:** ["+oldDealer.getBankDetails().getBeneficiaryName()+"]\n" + //
                        "- **Bank Name:** ["+oldDealer.getBankDetails().getBankName()+"]\n" + //
                        "- **Branch Name:** ["+oldDealer.getBankDetails().getBranchName()+"]\n" + //
                        "- **Account Number:** ["+oldDealer.getBankDetails().getAccountNumber()+"]\n" + //
                        "- **IFSC Code:** ["+oldDealer.getBankDetails().getIfsccode()+"]\n" + //
                        "- **UPI ID:** ["+oldDealer.getBankDetails().getUpiid()+"]\n" + //
                        "\n" + //
                        "\n" + //
                        "Kindly ensure that all relevant records, systems, and communications reflect these updated details.");

        mailSender.send(message);
    }

    public void sendDeletionService(String service) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(upay_team);
        message.setSubject("Service Discontinuation Notice: ["+service+"]");
        message.setText("Hi Team,\n" + //
                        "\n" + //
                        "This is to inform you that the service **\"["+service+"]\"** has been officially **discontinued/deleted** and is no longer available for use.\n" + //
                        "\n" +
                        "Please ensure that any dependencies on this service are reviewed and updated accordingly to avoid disruptions.\n" + //
                        "\n" + //
                        "Thank you for your understanding.");

        mailSender.send(message);
    }

    public void sendDeletionDealer(Dealers dealer) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(upay_team);
        message.setSubject("Dealer Details Deleted – ["+dealer.getCompanyName()+"]");
        message.setText("Hi Team,\n" + //
                        "\n" + //
                        "Please note that the details for the following dealer have been successfully updated in our system.\n" + //
                        "\n" + //
                        "🔄 **Deleted Dealer Information:**\n" + //
                        "- **Dealer Name:** ["+dealer.getCompanyName()+"]\n" + //
                        "- **Dealer ID / Code:** ["+dealer.getId()+"]\n" + //
                        "- **Business Email:** ["+dealer.getBusinessEmail()+"]\n" + //
                        "- **Business Phone Number:** ["+dealer.getBusinessPhoneNumber()+"]\n" + //
                        "- **GST Number:** ["+dealer.getGstNumber()+"]\n" + //
                        "- **Beneficiary Name:** ["+dealer.getBankDetails().getBeneficiaryName()+"]\n" + //
                        "- **Bank Name:** ["+dealer.getBankDetails().getBankName()+"]\n" + //
                        "- **Branch Name:** ["+dealer.getBankDetails().getBranchName()+"]\n" + //
                        "- **Account Number:** ["+dealer.getBankDetails().getAccountNumber()+"]\n" + //
                        "- **IFSC Code:** ["+dealer.getBankDetails().getIfsccode()+"]\n" + //
                        "- **UPI ID:** ["+dealer.getBankDetails().getUpiid()+"]\n" + //
                        "\n" + //
                        "\n" + //
                        "Kindly ensure that all relevant records, systems, and communications reflect these updated details.");

        mailSender.send(message);
    }

    @Value("${upay_team}")
    private String upay_team; 
}
