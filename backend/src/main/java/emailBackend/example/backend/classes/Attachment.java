package emailBackend.example.backend.classes;

import java.util.Arrays;

public class Attachment {
    private byte[] attachment;
    private String attType;
    private String attName;

    public String getAttName() {
        return attName;
    }

    public void setAttName(String attName) {
        this.attName = attName;
    }

   

    @Override
    public String toString() {
        return "Attachment [attachment=" + Arrays.toString(attachment) + ", attType=" + attType + ", attName=" + attName
                + "]";
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    public String getAttType() {
        return attType;
    }

    public void setAttType(String attType) {
        this.attType = attType;
    }
}