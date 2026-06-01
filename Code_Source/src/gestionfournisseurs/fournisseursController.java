package gestionfournisseurs;

import classes.beans.Fournisseurs;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.List;

public class fournisseursController implements Initializable {

    @FXML private TableView<Fournisseurs>            tableView;
    @FXML private TableColumn<Fournisseurs, Integer> colId;
    @FXML private TableColumn<Fournisseurs, String>  colCode;
    @FXML private TableColumn<Fournisseurs, String>  colNom;
    @FXML private TableColumn<Fournisseurs, String>  colTelephone;
    @FXML private TableColumn<Fournisseurs, String>  colEmail;
    @FXML private TableColumn<Fournisseurs, String>  colVille;
    @FXML private TableColumn<Fournisseurs, Boolean> colActif;
    @FXML private TableColumn<Fournisseurs, Integer> colCategorieId;

    @FXML private TextField codetext;
    @FXML private TextField nomtext;
    @FXML private TextField telephonetext;
    @FXML private TextField emailtext;
    @FXML private TextField villetext;
    @FXML private TextField categorietext;
    @FXML private TextField searchtext;

    @FXML private Label statusLabel;
    @FXML private Label lblTotalFournisseurs;
    @FXML private Label lblTotalVilles;
    @FXML private PieChart categoriePieChart;
    @FXML private TextField usernameField;
    @FXML private javafx.scene.control.PasswordField passwordField;
    @FXML private Label loginStatus;
    @FXML private javafx.scene.control.Tab tabFormulaire;
    @FXML private javafx.scene.control.Tab tabListe;
    @FXML private javafx.scene.control.Tab tabDashboard;
    @FXML private javafx.scene.control.TabPane mainTabPane;

    // FIX : on garde l'ID séparément pour la suppression/modification
    private int selectedId = -1;
    private Fournisseurs selectedFournisseur = null;

    private ObservableList<Fournisseurs> allData = FXCollections.observableArrayList();

    private SessionFactory getFactory() {
        return new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tabFormulaire.setDisable(true);
        tabListe.setDisable(true);
        tabDashboard.setDisable(true);

        colId         .setCellValueFactory(new PropertyValueFactory<>("id"));
        colCode       .setCellValueFactory(new PropertyValueFactory<>("code"));
        colNom        .setCellValueFactory(new PropertyValueFactory<>("nom"));
        colTelephone  .setCellValueFactory(new PropertyValueFactory<>("telephone"));
        colEmail      .setCellValueFactory(new PropertyValueFactory<>("email"));
        colVille      .setCellValueFactory(new PropertyValueFactory<>("ville"));
        colActif      .setCellValueFactory(new PropertyValueFactory<>("actif"));
        colCategorieId.setCellValueFactory(new PropertyValueFactory<>("categorie_id"));

        chargerDonnees();

        tableView.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldVal, newVal) -> {
                if (newVal != null) {
                    selectedFournisseur = newVal;
                    // FIX : stocker l'ID dans une variable primitive pour éviter les
                    // problèmes de détachement Hibernate
                    selectedId = newVal.getId();

                    codetext     .setText(newVal.getCode());
                    nomtext      .setText(newVal.getNom());
                    telephonetext.setText(newVal.getTelephone());
                    emailtext    .setText(newVal.getEmail());
                    villetext    .setText(newVal.getVille() != null ? newVal.getVille() : "");
                    categorietext.setText(String.valueOf(newVal.getCategorie_id()));
                    setStatus("Fournisseur sélectionné : " + newVal.getNom(), false);
                }
            }
        );
    }

    private void chargerDonnees() {
        SessionFactory factory = null;
        Session session = null;
        try {
            factory = getFactory();
            session = factory.openSession();

            @SuppressWarnings("unchecked")
            List<Fournisseurs> liste = session.createQuery("FROM Fournisseurs").list();
            allData = FXCollections.observableArrayList(liste);
            tableView.setItems(allData);
            genererStatistiques();

        } catch (Exception e) {
            setStatus("Erreur chargement : " + e.getMessage(), true);
        } finally {
            if (session != null) session.close();
            if (factory != null) factory.close();
        }
    }

    private void genererStatistiques() {
        if (allData == null || allData.isEmpty()) return;

        if (lblTotalFournisseurs != null)
            lblTotalFournisseurs.setText(String.valueOf(allData.size()));

        java.util.HashSet<String> villesUniques = new java.util.HashSet<>();
        Map<String, Integer> repartition = new HashMap<>();

        for (Fournisseurs f : allData) {
            String ville = (f.getVille() != null && !f.getVille().trim().isEmpty())
                           ? f.getVille().trim() : "Inconnue";
            villesUniques.add(ville);
            repartition.put(ville, repartition.containsKey(ville) ? repartition.get(ville) + 1 : 1);
        }

        if (lblTotalVilles != null)
            lblTotalVilles.setText(villesUniques.size() + " Villes");

        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> e : repartition.entrySet())
            pieData.add(new PieChart.Data(e.getKey() + " (" + e.getValue() + ")", e.getValue()));

        if (categoriePieChart != null)
            categoriePieChart.setData(pieData);
    }

    @FXML
    private void handleSaved() {
        if (!validerFormulaire()) return;
        SessionFactory factory = null;
        Session session = null;
        try {
            factory = getFactory();
            session = factory.openSession();
            session.beginTransaction();

            session.createSQLQuery(
                "INSERT INTO fournisseur (code, nom, telephone, email, ville, actif, categorie_id) " +
                "VALUES (:code, :nom, :tel, :email, :ville, :actif, :catId)")
                .setParameter("code",  codetext.getText().trim())
                .setParameter("nom",   nomtext.getText().trim())
                .setParameter("tel",   telephonetext.getText().trim())
                .setParameter("email", emailtext.getText().trim())
                .setParameter("ville", villetext.getText().trim())
                .setParameter("actif", true)
                .setParameter("catId", parseCategorieId())
                .executeUpdate();

            session.getTransaction().commit();
            chargerDonnees();
            handleAnnuler();
            setStatus("Fournisseur ajouté avec succès !", false);

        } catch (Exception e) {
            if (session != null && session.getTransaction() != null)
                session.getTransaction().rollback();
            setStatus("Erreur ajout : " + e.getMessage(), true);
        } finally {
            if (session != null) session.close();
            if (factory != null) factory.close();
        }
    }

    @FXML
    private void handleModifier() {
        if (selectedId == -1) {
            setStatus("Veuillez sélectionner un fournisseur dans la liste.", true);
            return;
        }
        if (!validerFormulaire()) return;
        SessionFactory factory = null;
        Session session = null;
        try {
            factory = getFactory();
            session = factory.openSession();
            session.beginTransaction();

            // FIX : utiliser selectedId (int) au lieu de selectedFournisseur.getId()
            // pour éviter les problèmes d'objet détaché Hibernate
            Fournisseurs f = (Fournisseurs) session.get(Fournisseurs.class, selectedId);
            if (f != null) {
                f.setCode(codetext.getText().trim());
                f.setNom(nomtext.getText().trim());
                f.setTelephone(telephonetext.getText().trim());
                f.setEmail(emailtext.getText().trim());
                f.setVille(villetext.getText().trim());
                session.update(f);
            }

            session.getTransaction().commit();
            chargerDonnees();
            handleAnnuler();
            setStatus("Fournisseur modifié avec succès !", false);

        } catch (Exception e) {
            if (session != null && session.getTransaction() != null)
                session.getTransaction().rollback();
            setStatus("Erreur modification : " + e.getMessage(), true);
        } finally {
            if (session != null) session.close();
            if (factory != null) factory.close();
        }
    }

   @FXML
private void handleSupprimer() {
    if (selectedId == -1) {
        setStatus("Veuillez sélectionner un fournisseur dans la liste.", true);
        return;
    }
    SessionFactory factory = null;
    Session session = null;
    try {
        factory = getFactory();
        session = factory.openSession();
        session.beginTransaction();

        // FIX : supprimer d'abord les produits liés (contrainte FK)
        session.createSQLQuery(
            "DELETE FROM produit WHERE fournisseur_id = :id")
            .setParameter("id", selectedId)
            .executeUpdate();

        // Ensuite supprimer le fournisseur
        session.createSQLQuery(
            "DELETE FROM fournisseur WHERE id = :id")
            .setParameter("id", selectedId)
            .executeUpdate();

        session.getTransaction().commit();
        chargerDonnees();
        handleAnnuler();
        setStatus("Fournisseur supprimé avec succès !", false);

    } catch (Exception e) {
        if (session != null && session.getTransaction() != null)
            session.getTransaction().rollback();
        setStatus("Erreur suppression : " + e.getMessage(), true);
    } finally {
        if (session != null) session.close();
        if (factory != null) factory.close();
    }
}

    @FXML
    private void handleAnnuler() {
        codetext.clear();
        nomtext.clear();
        telephonetext.clear();
        emailtext.clear();
        villetext.clear();
        categorietext.clear();
        selectedFournisseur = null;
        // FIX : réinitialiser aussi selectedId
        selectedId = -1;
        tableView.getSelectionModel().clearSelection();
        setStatus("", false);
    }

    @FXML
    private void handleActualiser() {
        chargerDonnees();
        setStatus("Liste actualisée.", false);
    }

    @FXML
    private void handleSearch() {
        String keyword = searchtext.getText().toLowerCase().trim();
        if (keyword.isEmpty()) {
            tableView.setItems(allData);
        } else {
            ObservableList<Fournisseurs> filtered = FXCollections.observableArrayList();
            for (Fournisseurs f : allData) {
                if ((f.getNom()   != null && f.getNom()  .toLowerCase().contains(keyword)) ||
                    (f.getCode()  != null && f.getCode() .toLowerCase().contains(keyword)) ||
                    (f.getVille() != null && f.getVille().toLowerCase().contains(keyword))) {
                    filtered.add(f);
                }
            }
            tableView.setItems(filtered);
        }
    }

    private boolean validerFormulaire() {
        if (codetext.getText().trim().isEmpty() ||
            nomtext.getText().trim().isEmpty()  ||
            telephonetext.getText().trim().isEmpty() ||
            emailtext.getText().trim().isEmpty()) {
            setStatus("Code, Nom, Téléphone et Email sont obligatoires.", true);
            return false;
        }
        return true;
    }

    private int parseCategorieId() {
        try { return Integer.parseInt(categorietext.getText().trim()); }
        catch (NumberFormatException e) { return 1; }
    }

    private void setStatus(String msg, boolean isError) {
        if (statusLabel == null) return;
        statusLabel.setText(msg);
        statusLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: " +
                             (isError ? "#dc3545" : "#0598ff") + ";");
    }

    @FXML
    private void handleLogin() {
        String user = usernameField.getText().trim();
        String pass = passwordField.getText().trim();

        if (user.isEmpty() || pass.isEmpty()) {
            loginStatus.setText("Veuillez remplir tous les champs !");
            loginStatus.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
            return;
        }

        if (user.equals("admin") && pass.equals("1234")) {
            tabFormulaire.setDisable(false);
            tabListe.setDisable(false);
            tabDashboard.setDisable(false);

            loginStatus.setText("Connexion réussie !");
            loginStatus.setStyle("-fx-text-fill: green; -fx-font-size: 12px;");

            // FIX : rediriger directement vers l'onglet Formulaire après connexion
            mainTabPane.getSelectionModel().select(tabFormulaire);

        } else {
            loginStatus.setText("Identifiants incorrects !");
            loginStatus.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
        }
    }

    @FXML
    private void handleDeconnexion() {
        tabFormulaire.setDisable(true);
        tabListe.setDisable(true);
        tabDashboard.setDisable(true);

        usernameField.clear();
        passwordField.clear();
        loginStatus.setText("");

        mainTabPane.getSelectionModel().selectFirst();
    }
}