import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vikno on 2/19/2016.
 */
public class Controller {

    /*
    Координати міст на карті
     */
    private static Map<String,Coordinates> coord = new HashMap<String, Coordinates>();
    static {
        coord.put("Львів",new Coordinates(101,204));
        coord.put("Луцьк",new Coordinates(174,129));
        coord.put("Ужгород",new Coordinates(31,294));
        coord.put("Рівне",new Coordinates(211,145));
        coord.put("Житомир",new Coordinates(340,173));
        coord.put("Івано-Франківськ",new Coordinates(132,276));
        coord.put("Тернопіль",new Coordinates(184,220));
        coord.put("Хмельницький",new Coordinates(250,236));
        coord.put("Київ",new Coordinates(427,157));
        coord.put("Чернігів",new Coordinates(465,86));
        coord.put("Суми",new Coordinates(639,121));
        coord.put("Харків",new Coordinates(709,189));
        coord.put("Вінниця",new Coordinates(330,252));
        coord.put("Черкаси",new Coordinates(503,244));
        coord.put("Одеса",new Coordinates(437,458));
        coord.put("Чернівці",new Coordinates(197,320));
        coord.put("Кіровоград",new Coordinates(522,310));
        coord.put("Полтава",new Coordinates(630,220));
        coord.put("Дніпропетровськ",new Coordinates(658,305));
        coord.put("Миколаїв",new Coordinates(503,416));
        coord.put("Запоріжжя",new Coordinates(658,357));
        coord.put("Сімферополь",new Coordinates(611,559));
        coord.put("Херсон",new Coordinates(532,442));
        coord.put("Донецьк",new Coordinates(790,349));
        coord.put("Луганськ",new Coordinates(863,302));
    }

    @FXML
    AnchorPane Pane;

    @FXML
    Button calcPath;
    @FXML
    Button clear;
    @FXML
    Button help;

    @FXML
    TextField pathLength;
    @FXML
    TextField Start;
    @FXML
    TextField End;

    Line[] lines;

//    private static  Graph.Edge[] GRAPH;

//    public void init() throws IOException {
//            BufferedReader reader = new BufferedReader(new FileReader("/Citys.txt"));
//            ArrayList<Graph.Edge> edges = new ArrayList<Graph.Edge>();
//
//            while (reader.ready()) {
//                String line = reader.readLine();
//                String cityOne = line.split(" ")[0];
//                String cityTwo = line.split(" ")[1];
//                int dist = Integer.parseInt(line.split(" ")[2]);
//                edges.add(new Graph.Edge(cityOne, cityTwo, dist));
//            }
//
//            GRAPH = new Graph.Edge[edges.size()];
//            for (int i = 0; i < edges.size(); i++) {
//                GRAPH[i] = edges.get(i);
//            }
//    }

    /*
    Визначення мінімально шляху і проведення його на карті
     */
    public void calcMinPath() throws IOException {

//        init();

        String startCity = Start.getText();
        String endCity = End.getText();

        Graph g = new Graph(GRAPH);
        g.dijkstra(startCity);
        g.printPath(endCity);
        System.out.println(g.resPath);
        System.out.println(g.distance);

        String citys[] = g.resPath.split(" ");

        Line[] line = new Line[citys.length-1];


        for(int i=0;i<citys.length-1;i++) {
            line[i] = new Line();
            line[i].setStroke(Color.DARKBLUE);
            line[i].setStrokeWidth(5);
            line[i].setStrokeLineCap(StrokeLineCap.ROUND);
            line[i].getStrokeDashArray().addAll(5.0,20.0);
            line[i].setStartX(coord.get(citys[i]).x);
            line[i].setStartY(coord.get(citys[i]).y);
            line[i].setEndX(coord.get(citys[i+1]).x);
            line[i].setEndY(coord.get(citys[i+1]).y);

            Pane.getChildren().add(line[i]);
        }

        lines = line;
        g.resPath="";
        pathLength.setText(g.distance+" км.");


    }

    /*
    Очищення шляху
     */
    public void clearPath() {
        try {
            Pane.getChildren().removeAll(lines);
        }
        catch (Exception e){
            System.out.println("Нема елементів для видалення!");
        }
    }

    /*
    Допомога
     */
    public void printHelp() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Як користуватися?");
        alert.setHeaderText("Знаходження мінімально шляху\nміж містами.");
        alert.setContentText("Введіть початкове і кінцеве місто\nта натисніть кнопку знайти шлях.\n\nВиконав Мальчишин Роман,КН-45");
        alert.showAndWait();
    }

    /*
    Координати областей
     */
    static class Coordinates{
        int x;
        int y;
        Coordinates(int x,int y){
            this.x = x;
            this.y = y;
        }
    }


    /*
    Граф вершин областей
     */
    private static final Graph.Edge[] GRAPH = {
            new Graph.Edge("Львів", "Луцьк", 175),
            new Graph.Edge("Львів", "Рівне", 209),
            new Graph.Edge("Львів", "Тернопіль", 154),
            new Graph.Edge("Львів", "Івано-Франківськ", 139),
            new Graph.Edge("Львів", "Ужгород", 226),
            new Graph.Edge("Тернопіль", "Рівне", 164),
            new Graph.Edge("Тернопіль", "Івано-Франківськ", 128),
            new Graph.Edge("Тернопіль", "Хмельницький", 140),
            new Graph.Edge("Тернопіль", "Чернівці", 182),
            new Graph.Edge("Тернопіль", "Луцьк", 163),
            new Graph.Edge("Тернопіль", "Львів", 154),
            new Graph.Edge("Луцьк", "Львів", 175),
            new Graph.Edge("Луцьк", "Рівне", 101),
            new Graph.Edge("Луцьк", "Тернопіль", 163),
            new Graph.Edge("Луцьк", "Хмельницький", 224),
            new Graph.Edge("Луцьк", "Ужгород", 363),
            new Graph.Edge("Рівне", "Львів", 209),
            new Graph.Edge("Рівне", "Тернопіль", 164),
            new Graph.Edge("Рівне", "Хмельницький", 168),
            new Graph.Edge("Рівне", "Житомир", 211),
            new Graph.Edge("Хмельницький", "Рівне", 168),
            new Graph.Edge("Хмельницький", "Львів", 255),
            new Graph.Edge("Хмельницький", "Вінниця", 147),
            new Graph.Edge("Хмельницький", "Житомир", 186),
            new Graph.Edge("Хмельницький", "Тернопіль", 140),
            new Graph.Edge("Хмельницький", "Київ", 300),
            new Graph.Edge("Ужгород", "Тернопіль", 294),
            new Graph.Edge("Ужгород", "Львів", 226),
            new Graph.Edge("Ужгород", "Івано-Франківськ", 220),
            new Graph.Edge("Ужгород", "Чернівці", 308),
            new Graph.Edge("Чернівці", "Ужгород", 308),
            new Graph.Edge("Чернівці", "Тернопіль", 163),
            new Graph.Edge("Чернівці", "Івано-Франківськ", 128),
            new Graph.Edge("Чернівці", "Вінниця", 252),
            new Graph.Edge("Івано-Франківськ", "Ужгород", 220),
            new Graph.Edge("Івано-Франківськ", "Львів", 139),
            new Graph.Edge("Івано-Франківськ", "Тернопіль", 128),
            new Graph.Edge("Івано-Франківськ", "Хмельницький", 210),
            new Graph.Edge("Івано-Франківськ", "Чернівці", 128),
            new Graph.Edge("Вінниця", "Івано-Франківськ", 311),
            new Graph.Edge("Вінниця", "Житомир", 154),
            new Graph.Edge("Вінниця", "Тернопіль", 252),
            new Graph.Edge("Вінниця", "Хмельницький", 147),
            new Graph.Edge("Вінниця", "Чернівці", 252),
            new Graph.Edge("Вінниця", "Київ", 239),
            new Graph.Edge("Вінниця", "Черкаси", 283),
            new Graph.Edge("Житомир", "Черкаси", 224),
            new Graph.Edge("Житомир", "Київ", 173),
            new Graph.Edge("Житомир", "Рівне", 203),
            new Graph.Edge("Житомир", "Хмельницький", 186),
            new Graph.Edge("Житомир", "Вінниця", 154),
            new Graph.Edge("Житомир", "Чернігів", 259),
            new Graph.Edge("Київ", "Чернігів", 161),
            new Graph.Edge("Київ", "Житомир", 173),
            new Graph.Edge("Київ", "Хмельницький", 300),
            new Graph.Edge("Київ", "Вінниця", 239),
            new Graph.Edge("Київ", "Черкаси", 188),
            new Graph.Edge("Київ", "Суми", 336),
            new Graph.Edge("Київ", "Полтава", 328),
            new Graph.Edge("Черкаси", "Полтава", 206),
            new Graph.Edge("Черкаси", "Київ", 188),
            new Graph.Edge("Черкаси", "Вінниця", 283),
            new Graph.Edge("Черкаси", "Кіровоград", 133),
            new Graph.Edge("Черкаси", "Суми", 284),
            new Graph.Edge("Чернігів", "Суми", 283),
            new Graph.Edge("Чернігів", "Київ", 161),
            new Graph.Edge("Чернігів", "Полтава", 346),
            new Graph.Edge("Чернігів", "Черкаси", 271),
            new Graph.Edge("Чернігів", "Житомир", 259),
            new Graph.Edge("Суми", "Чернігів", 283),
            new Graph.Edge("Суми", "Київ", 336),
            new Graph.Edge("Суми", "Черкаси", 284),
            new Graph.Edge("Суми", "Полтава", 180),
            new Graph.Edge("Суми", "Харків", 177),
            new Graph.Edge("Харків", "Суми", 177),
            new Graph.Edge("Харків", "Полтава", 163),
            new Graph.Edge("Харків", "Київ", 443),
            new Graph.Edge("Харків", "Луганськ", 310),
            new Graph.Edge("Харків", "Донецьк", 271),
            new Graph.Edge("Харків", "Дніпропетровськ", 224),
            new Graph.Edge("Полтава", "Суми", 180),
            new Graph.Edge("Полтава", "Київ", 328),
            new Graph.Edge("Полтава", "Чернігів", 346),
            new Graph.Edge("Полтава", "Черкаси", 206),
            new Graph.Edge("Полтава", "Харків", 163),
            new Graph.Edge("Полтава", "Кіровоград", 245),
            new Graph.Edge("Полтава", "Дніпропетровськ", 161),
            new Graph.Edge("Кіровоград", "Дніпропетровськ", 238),
            new Graph.Edge("Кіровоград", "Полтава", 245),
            new Graph.Edge("Кіровоград", "Київ", 272),
            new Graph.Edge("Кіровоград", "Вінниця", 233),
            new Graph.Edge("Кіровоград", "Миколаїв", 203),
            new Graph.Edge("Кіровоград", "Одеса", 291),
            new Graph.Edge("Дніпропетровськ", "Кіровоград", 238),
            new Graph.Edge("Дніпропетровськ", "Київ", 413),
            new Graph.Edge("Дніпропетровськ", "Харків", 224),
            new Graph.Edge("Дніпропетровськ", "Полтава", 161),
            new Graph.Edge("Дніпропетровськ", "Черкаси", 270),
            new Graph.Edge("Дніпропетровськ", "Миколаїв", 315),
            new Graph.Edge("Дніпропетровськ", "Запоріжжя", 108),
            new Graph.Edge("Дніпропетровськ", "Луганськ", 367),
            new Graph.Edge("Дніпропетровськ", "Донецьк", 268),
            new Graph.Edge("Одеса", "Вінниця", 378),
            new Graph.Edge("Одеса", "Київ", 508),
            new Graph.Edge("Одеса", "Миколаїв", 136),
            new Graph.Edge("Одеса", "Херсон", 171),
            new Graph.Edge("Миколаїв", "Херсон", 92),
            new Graph.Edge("Миколаїв", "Одеса", 136),
            new Graph.Edge("Миколаїв", "Кіровоград", 205),
            new Graph.Edge("Миколаїв", "Вінниця", 392),
            new Graph.Edge("Миколаїв", "Запоріжжя", 299),
            new Graph.Edge("Миколаїв", "Київ", 444),
            new Graph.Edge("Херсон", "Миколаїв", 92),
            new Graph.Edge("Херсон", "Одеса", 171),
            new Graph.Edge("Херсон", "Кіровоград", 236),
            new Graph.Edge("Херсон", "Запоріжжя", 273),
            new Graph.Edge("Херсон", "Сімферополь", 255),
            new Graph.Edge("Сімферополь", "Херсон", 255),
            new Graph.Edge("Запоріжжя", "Херсон", 273),
            new Graph.Edge("Запоріжжя", "Дніпропетровськ", 108),
            new Graph.Edge("Запоріжжя", "Миколаїв", 299),
            new Graph.Edge("Запоріжжя", "Кіровоград", 266),
            new Graph.Edge("Запоріжжя", "Донецьк", 238),
            new Graph.Edge("Запоріжжя", "Луганськ", 35),
            new Graph.Edge("Донецьк", "Луганськ", 168),
            new Graph.Edge("Донецьк", "Запоріжжя", 238),
            new Graph.Edge("Донецьк", "Дніпропетровськ", 268),
            new Graph.Edge("Донецьк", "Харків", 271),
            new Graph.Edge("Луганськ", "Харків", 310),
            new Graph.Edge("Луганськ", "Донецьк", 168),
            new Graph.Edge("Луганськ", "Запоріжжя", 350),
            new Graph.Edge("Луганськ", "Дніпропетровськ", 357)};
}