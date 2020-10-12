import org.h2.tools.Csv;
import org.h2.tools.SimpleResultSet;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Application {
    private static List<List<List<Integer>>> pipeline;
    public static void main(String[] args) throws SQLException {
        List<Object[]> result = new ArrayList<>();
        DepthFirstSearch searchAlgorithm = new DepthFirstSearch();
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm();
        pipeline = readWaterPipeline();
        List<List<Integer>> pointSet = readPointSet();

        searchAlgorithm.setGraph(pipeline);
        dijkstraAlgorithm.setGraph(pipeline);

        for (List<Integer> point : pointSet) {
            if (searchAlgorithm.isConnected(point.get(0),point.get(1))) {
                int distance = dijkstraAlgorithm.shortestPath(point.get(0),point.get(1));
                result.add(new Object[]{true, distance});
            } else {
                result.add(new Object[]{false});
            }

        }

        writeResult(result);
    }

    private static List<List<List<Integer>>> readWaterPipeline() throws SQLException {
        ResultSet rs = new Csv().read("data/WaterPipeline.csv", null, null);
        int size = getNodeNumber();
        List<List<List<Integer>>> pipelineSystem = new ArrayList<>();
        for(int i=0;i < size+1; i++) {
            pipelineSystem.add(new ArrayList<>());
        }


        ResultSetMetaData meta = rs.getMetaData();
        while (rs.next()) {
            int x = Integer.parseInt(rs.getString(1));
            int y = Integer.parseInt(rs.getString(2));
            int len = Integer.parseInt(rs.getString(3));

            List<List<Integer>> innerList = pipelineSystem.get(x);
            innerList.add(new ArrayList<>() {
                {
                    add(y);
                    add(len);
                }
            });

            pipelineSystem.set(x, innerList);

            innerList = pipelineSystem.get(y);
            innerList.add(new ArrayList<>() {
                {
                    add(x);
                    add(len);
                }
            });

            pipelineSystem.set(y, innerList);
        }
        rs.close();

        return pipelineSystem;
    }

    private static List<List<Integer>> readPointSet() throws SQLException {
        List<List<Integer>> pointSet = new ArrayList<>();
        ResultSet rs = new Csv().read("data/PointsSet.csv", null, null);
        ResultSetMetaData meta = rs.getMetaData();

        while (rs.next()) {
            int x = Integer.parseInt(rs.getString(1));
            int y = Integer.parseInt(rs.getString(2));

            pointSet.add(new ArrayList<>() {
                {
                    add(x);
                    add(y);
                }
            });
        }
        rs.close();

        return pointSet;
    }

    private static void writeResult(List<Object[]> list) throws SQLException {
        SimpleResultSet rs = new SimpleResultSet();
        rs.addColumn("Exists", Types.BOOLEAN, 5, 0);
        rs.addColumn("Length", Types.INTEGER,5,0);
        for (Object[] row : list) {
            rs.addRow(row);
        }
        new Csv().write("data/Answer.csv", rs, null);
    }

    private static int getNodeNumber() throws SQLException {
        int max = Integer.MIN_VALUE;

        ResultSet rs = new Csv().read("data/WaterPipeline.csv", null, null);

        ResultSetMetaData meta = rs.getMetaData();
        while (rs.next()) {
            int x = Integer.parseInt(rs.getString(1));
            int y = Integer.parseInt(rs.getString(2));
            int len = Integer.parseInt(rs.getString(3));

            max = Math.max(max, Math.max(x, y));
        }
        rs.close();
        return max;
    }
}
