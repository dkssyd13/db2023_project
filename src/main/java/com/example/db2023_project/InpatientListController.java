package com.example.db2023_project;

import com.example.db2023_project.DB.Database;
import com.example.db2023_project.Model.Inpatient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


public class InpatientListController implements Initializable {
    /*
    * 현재 입원한 환자들의 List를 갖고 오는 컨트롤러.
    * inpatientsListView : UI에 보여줄 String들의 리스트.
    * inpatients : db에서 갖고온 정보를 바탕으로 inpatient객체들을 만들고 이 리스트에 저장.
    * selectedInpatients : 사용자가 클릭(선택)한 환자들을 따로 담는 리스트.
    * */

    @FXML
    private TableView<Inpatient> inpatientsTableView;

    @FXML
    private TableColumn<Inpatient, String> nameColumn;

    @FXML
    private TableColumn<Inpatient, Long> roomIdColumn;

    @FXML
    private TableColumn<Inpatient, Long> secIdColumn;

    @FXML
    private Button startButton;

    private List<Inpatient> inpatients = new ArrayList<Inpatient>();

    private Set<Inpatient> selectedInpatients = new HashSet<Inpatient>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Database db = Database.getDb();
        Connection connection = db.getConnection();

        nameColumn.setCellValueFactory(new PropertyValueFactory<Inpatient, String>("name"));
        secIdColumn.setCellValueFactory(new PropertyValueFactory<Inpatient, Long>("secId"));
        roomIdColumn.setCellValueFactory(new PropertyValueFactory<Inpatient, Long>("roomId"));
        inpatientsTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);



//        inpatientsListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//
//                int selectedIndex = inpatientsListView.getSelectionModel().getSelectedIndex();
//                System.out.println("Clicked on item index: " + selectedIndex);
//
//                // 선택된 inpatient를 찾음
//                Inpatient selectedInpatient = inpatients.get(selectedIndex);
//
//                // ListView에서 아이템 텍스트 확인
//                String listItem = inpatientsListView.getItems().get(selectedIndex);
//
//                if (listItem.endsWith(" (선택됨)")) {
//                    // 이미 선택된 아이템이면 '선택됨' 표시 제거하고 selectedInpatients에서 제거
//                    listItem = listItem.substring(0, listItem.length() - " (선택됨)".length());
//                    selectedInpatients.remove(selectedInpatient);
//                } else {
//                    // 아직 선택되지 않은 아이템이면 '선택됨' 표시 추가하고 selectedInpatients에 추가
//                    listItem += " (선택됨)";
//                    selectedInpatients.add(selectedInpatient);
//                }
//
//                // ListView에서 아이템 텍스트 변경
//                inpatientsListView.getItems().set(selectedIndex, listItem);
//            }
//        });

        inpatientsTableView.setRowFactory(tv -> {
                    TableRow<Inpatient> row = new TableRow<>();
                    row.setOnMouseClicked(mouseEvent -> {
                        if(!row.isEmpty() && mouseEvent.getButton()== MouseButton.PRIMARY){
                            Inpatient clickedRow = row.getItem();
                            if(mouseEvent.getClickCount()==1){
                                //한번 클릭하면 배경이 초록색으로 바뀌고, 선택된 inpatients리스트에 넣는다
                                row.setStyle("-fx-background-color: green;");
                                selectedInpatients.add(clickedRow);
                                System.out.println("selectedInpatients.size() = " + selectedInpatients.size());
                            }else if(mouseEvent.getClickCount()==2){
                                //두번 클릭하면 배경이 원래 색으로 돌아오고, set(selectedInpatients)에서 삭제
                                row.setStyle("");
                                selectedInpatients.remove(clickedRow);
                                System.out.println("selectedInpatients.size() = " + selectedInpatients.size());
                            }
                        }
                    });
                    return row;
                }
                );

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
        * 위 쿼리가 너무 보기 힘들어서, personID, inpatientID, personName 등 필요할것 같은 정보만 뽑도록 쿼리 수정
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
                String diseaseID = "0"; // 현재 0으로 한 이유 db에 null값이 들어가 있어, 에러가 발생해서임.
//                String secID = queryOutput.getString(5);
                String secID = "0";// 현재 0으로 한 이유 db에 null값이 들어가 있어, 에러가 발생해서임.
//                String roomID = queryOutput.getString(6);
                String roomID = "0";// 현재 0으로 한 이유 db에 null값이 들어가 있어, 에러가 발생해서임.
                String hgrisk = "0";// 현재 0으로 한 이유 db에 null값이 들어가 있어, 에러가 발생해서임.

                Inpatient inpatientToAdd= new Inpatient(
                        Long.parseLong(personId),
                        Long.parseLong(inpatientID),
                        name,
                        Long.parseLong(diseaseID),
                        Long.parseLong(secID),
                        Long.parseLong(roomID),
                        Boolean.parseBoolean(hgrisk)
                );


                inpatients.add(inpatientToAdd);

                System.out.println("inpatients size = " + inpatients.size());


                String listItem = count + ") " + name;

                inpatientsTableView.getItems().add(inpatientToAdd);
                count++;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void startButtonOnAction(ActionEvent e){
        /*
         * 역학 조사 시작ㅎ
         * */
        for (Inpatient selectedInpatient : selectedInpatients) {
            System.out.println("selectedInpatient.getPersonId() = " + selectedInpatient.getPersonId());
            System.out.println("selectedInpatient.getInpatientId() = " + selectedInpatient.getInpatientId());
            System.out.println("selectedInpatient.getName() = " + selectedInpatient.getName());
        }
    }

}
