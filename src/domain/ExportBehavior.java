package domain;

public interface ExportBehavior {
    public void export(String exportPath, Order order) throws Exception;
}
