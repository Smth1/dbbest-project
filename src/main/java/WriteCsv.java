import org.h2.tools.Csv;
import org.h2.tools.SimpleResultSet;

import java.sql.*;

public class WriteCsv {
    public static void main(String[] args) throws Exception {
        writePipelineSystem();
        writePointsSet();
    }

    private static void writePipelineSystem() throws SQLException {
        SimpleResultSet rs = new SimpleResultSet();
        rs.addColumn("IDx", Types.INTEGER, 5, 0);
        rs.addColumn("IDy", Types.INTEGER, 5, 0);
        rs.addColumn("Length", Types.INTEGER,5,0);
        rs.addRow(1, 2, 10);
        rs.addRow(2, 3, 20);
        rs.addRow(3, 4, 30);
        rs.addRow(3, 5, 15);
        rs.addRow(6, 7, 20);
        new Csv().write("data/WaterPipeline.csv", rs, null);
    }

    private static void writePointsSet() throws SQLException {
        SimpleResultSet rs = new SimpleResultSet();
        rs.addColumn("IDa", Types.INTEGER, 5, 0);
        rs.addColumn("IDb", Types.INTEGER, 5, 0);
        rs.addRow(2,5);
        rs.addRow(2,6);
        rs.addRow(6,7);
        new Csv().write("data/PointsSet.csv", rs, null);
    }
}