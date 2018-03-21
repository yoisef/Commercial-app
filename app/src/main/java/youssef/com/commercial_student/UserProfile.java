package youssef.com.commercial_student;

import android.net.Uri;
import android.support.annotation.Nullable;

import com.google.firebase.auth.UserInfo;

/**
 * Created by mohamed on 17/03/2018.
 */

public class UserProfile {

    UserInfo profile=new UserInfo() {
        @Override
        public String getUid() {
            return null;
        }

        @Override
        public String getProviderId() {
            return null;
        }

        @Nullable
        @Override
        public String getDisplayName() {
            return null;
        }

        @Nullable
        @Override
        public Uri getPhotoUrl() {
            return null;
        }

        @Nullable
        @Override
        public String getEmail() {
            return null;
        }

        @Override
        public boolean isEmailVerified() {
            return false;
        }
    };
}
