import dao.DAOFactory;
import dao.SuperDAO;
import dao.custom.impl.UserDAOImpl;
import entity.Room;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateFactoryConfig;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(new FXMLLoader(getClass().getResource("/view/login_form.fxml")).load());
//        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.setTitle("Test");
//        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
        new Thread(() -> {
            loadHibernate();
        }).start();
    }

    private void loadHibernate() {
        Session session = HibernateFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        transaction.commit();
        session.close();
    }

    public static void aaa(){
        double cap = 21.01;
        double interest = 0.07;
        double profit = 0.00;

        for (int i = 0; i < 15; i++) {
            System.out.print("\nDay - " + (i+1) + "___ Capital : " + cap);
            double income = cap * interest;
            System.out.print("__Income : " + income);
            profit+=income;
            cap += income;
            System.out.print("__Profit : " + profit);
        }
    }
}
