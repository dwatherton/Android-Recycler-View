package edu.ualr.recyclerviewassignment.model;

public class SectionHeader extends Item {

    private String label;
    private Header header;

    public SectionHeader(Header header) {
        super(true);
        this.label = header.toString();
        this.header = header;
    }

    public String getLabel()
    {
        return label;
    }

    public Header getHeader()
    {
        return header;
    }

    public enum Header {
        Connected,
        Ready,
        Linked
    }
}
