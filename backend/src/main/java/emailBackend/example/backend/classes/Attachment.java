package emailBackend.example.backend.classes;

import java.util.Arrays;

public class Attachment {
    private byte[] attachment;
    private String attType;
    private String attName;
    private float attSize;
    public String getAttName() {
        return attName;
    }

    public void setAttName(String attName) {
        this.attName = attName;
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

    public float getAttSize() {
        return attSize;
    }

    public void setAttSize(float attSize) {
        this.attSize = attSize;
    }

    @Override
    public String toString() {
        return "Attachment [attachment=" + Arrays.toString(attachment) + ", attType=" + attType + ", attName=" + attName
                + ", attSize=" + attSize + "]";
    }

    
}