package service.user;

import model.Report;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import model.validation.UserValidator;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.List;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;

import static database.Constants.Roles.EMPLOYEE;

public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;

    public UserServiceImpl(UserRepository userRepository, RightsRolesRepository rightsRolesRepository) {
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    //metoda folosita de admin pentru a adauga un nou angajat
    @Override
    public Notification<Integer> registerEmployee(String username, String password) {
        Role employeeRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);

        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(password)//prima oara dau parola simpla pt a putea fi validata si apoi ii fac hash
                .setRoles(Collections.singletonList(employeeRole))//creeaza o lista imutabila si are o singura copie
                .build();

        //ca sa nu am validator in controller
        UserValidator userValidator = new UserValidator(user);

        boolean userValid = userValidator.validate();
        Notification<Integer> userRegisterNotification = new Notification<>();

        if (!userValid ){
            userValidator.getErrors().forEach(userRegisterNotification::addError);
            userRegisterNotification.setResult(-1);
        }
        else if (userRepository.existsByUsername(username))
        {
            userRegisterNotification.addError("Username already exists! Please try another one.");
        }
        else {
            user.setPassword(hashPassword(password));
            userRegisterNotification.setResult(userRepository.addEmployee(user));
        }

        return userRegisterNotification;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Notification<Report> generateReport(User user) {
        Notification<Report> report=userRepository.generateReport(user);
        String dest = "report.pdf";
        if(report.hasErrors())
        {
            return report;
        }

        try {
                PdfWriter writer = new PdfWriter(new File(dest));
                PdfDocument pdfDoc = new PdfDocument(writer);
                Document document = new Document(pdfDoc);

                document.add(new Paragraph(report.getResult().toString()));
                document.close();

                System.out.println("PDF generat cu succes la: " + dest);
            } catch (Exception e) {
                e.printStackTrace();
                report.addError("Something is wrong with PDF generator");
            }
            return report;
    }


    private String hashPassword(String password) {
        try {
            // Sercured Hash Algorithm - 256
            // 1 byte = 8 biți
            // 1 byte = 1 char
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
