package emailBackend.example.backend.classes;

import java.util.HashMap;
import java.util.Map;

public class Priority {
    private static final Map<String, Integer> prioritiesToInt = new HashMap<>();
    private static final Map<Integer, String> prioritiesToString = new HashMap<>();
    String priorityString;
    int priorityInt;
    static {
        prioritiesToInt.put("Urgent", 4);
        prioritiesToInt.put("Important", 3);
        prioritiesToInt.put("Moderate", 2);
        prioritiesToInt.put("Minor", 1);

        for (Map.Entry<String, Integer> en : prioritiesToInt.entrySet()) {
            String key = en.getKey();
            Integer val = en.getValue();
            prioritiesToString.put(val, key);
        }
    }
    public Priority(String priorityString)
    {
        this.priorityInt = prioritiesToInt.get(priorityString);
        this.priorityString = priorityString;
    }
    public Priority(int priorityInt)
    {
        this.priorityInt = priorityInt;
        this.priorityString = prioritiesToString.get(priorityInt);
    }
    public int getPriorityInt() {
        return this.priorityInt;
    }

    public String getPriorityString() {
        return this.priorityString;
    }

}
