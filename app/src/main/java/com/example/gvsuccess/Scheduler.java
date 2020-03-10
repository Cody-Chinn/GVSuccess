package com.example.gvsuccess;


import android.util.Log;

import com.google.android.gms.tasks.*;
import com.google.firebase.firestore.*;

import java.util.ArrayList;


public class Scheduler {
    DataAccess data;
    ArrayList<ScheduledSession> currentSess;
    public Scheduler(ArrayList<ScheduledSession> sesh) {
        data = new DataAccess();
        this.currentSess = sesh;
    }

    public boolean sessionAvailable(String tutorID, String date, long time) {
        boolean rtn = true;
        for(ScheduledSession sesh : this.currentSess) {
            if(sesh.getTutorID().equals(tutorID) && sesh.getDate().equals(date)) {
                if(sesh.getStartTime() == time) {
                    rtn = false;
                }
            }
        }

        return rtn;
    }

    public boolean scheduleSession(SuccessCenter center, String studentEmail, String tutorID, String date, long time, long length) {
        boolean rtn = sessionAvailable(tutorID, date, time);

        //if the session is available, add a new session
        if(rtn) {
            ScheduledSession newSession = new ScheduledSession(tutorID, studentEmail, center.getSuccessCenterCode()
                    , date, time, length);
            data.addSession(newSession);
        }

        return rtn;
    }


}
