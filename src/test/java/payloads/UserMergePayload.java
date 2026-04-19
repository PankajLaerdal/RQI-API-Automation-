package payloads;

import java.util.*;

public class UserMergePayload {

    public static Map<String, Object> getPayload() {

        Map<String, Object> payload = new HashMap<>();

        List<Map<String, Object>> users = new ArrayList<>();

        Map<String, Object> user1 = new HashMap<>();
        user1.put("user_id", 2100562358);
        user1.put("retained_course_ids", Arrays.asList(1093));

        Map<String, Object> user2 = new HashMap<>();
        user2.put("user_id", 2100562379);
        user2.put("retained_course_ids", Arrays.asList(1093));

        users.add(user1);
        users.add(user2);

        payload.put("merge_users", users);
        payload.put("reason", "Data correction");

        return payload;
    }
}