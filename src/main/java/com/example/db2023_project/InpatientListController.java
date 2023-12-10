package com.example.db2023_project;

import com.example.db2023_project.DB.Database;
import com.example.db2023_project.DB.Model.Inpatient;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;



public class InpatientListController implements Initializable {

    @FXML
    private ListView<String> inpatientsListView;
    @FXML
    private Button startButton;

    private List<Inpatient> inpatients = new ArrayList<Inpatient>();

    private List<Inpatient> selectedInpatients = new ArrayList<Inpatient>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Database db = Database.getDb();
        Connection connection = db.getConnection();


        inpatientsListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
//                int index = inpatientsListView.getSelectionModel().getSelectedIndex();
//                System.out.println("클릭된 아이템의 인덱스 : " + index);
//                System.out.println("list view 내의 name : " + inpatientsListView.getItems().get(index));
//                System.out.println("inpatients List 내의 name : " + inpatients.get(index).getName());

                int selectedIndex = inpatientsListView.getSelectionModel().getSelectedIndex();
                System.out.println("Clicked on item index: " + selectedIndex);

                // 선택된 inpatient를 찾음
                Inpatient selectedInpatient = inpatients.get(selectedIndex);

                // ListView에서 아이템 텍스트 확인
                String listItem = inpatientsListView.getItems().get(selectedIndex);

                if (listItem.endsWith(" (표시됨)")) {
                    // 이미 표시된 아이템이면 '표시됨' 표시 제거하고 selectedInpatients에서 제거
                    listItem = listItem.substring(0, listItem.length() - " (표시됨)".length());
                    selectedInpatients.remove(selectedInpatient);
                } else {
                    // 아직 표시되지 않은 아이템이면 '표시됨' 표시 추가하고 selectedInpatients에 추가
                    listItem += " (표시됨)";
                    selectedInpatients.add(selectedInpatient);
                }

                // ListView에서 아이템 텍스트 변경
                inpatientsListView.getItems().set(selectedIndex, listItem);
            }
        });

        /**
         * 쿼리 설명
         * 일단 person, patient, inpatient를 join 하고,
         * 거기서 입원환자의 퇴원날짜(endDate)가 NULL 인 애들을 출력.
         * 즉, 아직 퇴원 안한 사람들만.
         */
//        String getInpatientListQuery = "SELECT * " +
//                "FROM person " +
//                "JOIN patient ON person.personID = patient.patientID " +
//                "JOIN inpatient ON patient.patientID = inpatient.patientID " +
//                "WHERE inpatient.endDate IS NULL;";

        /*
        * 위 쿼리가 너무 보기 힘들어서, personID, inpatientID, personName 등 필요할것 같은 정보가 표시하도록 쿼리 수정
        * */

        String getInpatientListQuery = "SELECT person.personID, inpatient.inpatientID, person.personName, inpatient.diseaseID, inpatient.secID,inpatient.roomID,inpatient.hgrisk\n" +
                "FROM person\n" +
                "JOIN patient ON person.personID = patient.patientID\n" +
                "JOIN inpatient ON patient.patientID = inpatient.patientID;";

        try {
            Statement statement = connection.createStatement();
            ResultSet queryOutput = statement.executeQuery(getInpatientListQuery);

            Long count = 1L;

            while (queryOutput.next()) {
                String personId = queryOutput.getString(1);
                String inpatientID = queryOutput.getString(2);
                String name = queryOutput.getString(3);
//                String diseaseID = queryOutput.getString(4);
                String diseaseID = "0";
//                String secID = queryOutput.getString(5);
                String secID = "0";
//                String roomID = queryOutput.getString(6);
                String roomID = "0";
                String hgrisk = "0";


                inpatients.add(new Inpatient(
                        Long.parseLong(personId),
                        Long.parseLong(inpatientID),
                        name,
                        Long.parseLong(diseaseID),
                        Long.parseLong(secID),
                        Long.parseLong(roomID),
                        Boolean.parseBoolean(hgrisk)
                ));

                System.out.println("inpatients size = " + inpatients.size());


                String listItem = count + ") " + name;

                inpatientsListView.getItems().add(listItem);
                count++;
            }

            /*
            * 역학 조사 시작
            * */

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void startButtonOnAction(ActionEvent e){
        for (Inpatient selectedInpatient : selectedInpatients) {
            System.out.println("selectedInpatient.getPersonId() = " + selectedInpatient.getPersonId());
            System.out.println("selectedInpatient.getInpatientId() = " + selectedInpatient.getInpatientId());
            System.out.println("selectedInpatient.getName() = " + selectedInpatient.getName());
        }
    }

}
