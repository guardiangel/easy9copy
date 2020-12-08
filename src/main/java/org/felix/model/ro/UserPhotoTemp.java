package org.felix.model.ro;

/**
 *用户头像
 */
public class UserPhotoTemp {

    private String id;

    private String userId;

    private String userPhoto;

    @Override
    public String toString() {
        return "UserPhotoTemp{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", userPhoto='" + userPhoto + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }
}
