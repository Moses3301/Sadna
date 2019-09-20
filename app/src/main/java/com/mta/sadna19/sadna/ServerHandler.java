package com.mta.sadna19.sadna;

import android.nfc.Tag;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.mta.sadna19.sadna.MenuRegisters.DataOption;
import com.mta.sadna19.sadna.MenuRegisters.Option;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ServerHandler {
    private DatabaseReference singleMenuRef, allMenuRef;
    private StorageReference storageRef;
    private StorageReference imageStorageRef;
    private static final String TAG = "onMenuFetcher";
    private OnServicesFetchedListener mOnServicesFetchedListener;
    private OnOptionFetchedListener mOnOptionFetcherListener;
    private OnUserFetchedListener mOnUserFetcherListener;
    private OnUserFetchedByEmailListener mOnUserFetchedByEmailListener;
    private OnAttributeFetchedListener mOnAttributeFetcherListener;
    private onLastCallFetchedListener mOnLastCallFetcherListener;
    private onFavoritesServicesFetchedListener mOnFavoritesServicesFetchedListener;
    private onPrivacyPolicyFetchedListener mOnPrivacyPolicyFetchedListener;
    private onProblemsFetchedListener mOnProblemsFetchedListener;
    private onAdminsFetchedListener mOnAdminsFetchedListener;

    public User getmUser() {
        return mUser;
    }

    public boolean isAdmin;
    private User mUser;
    private FirebaseUser fbUsr;

    public void deleteChild(String path , String end) {
        final DatabaseReference serviceRef = FirebaseDatabase.getInstance().getReference("Menus/" + path);
        Log.e(TAG,"onDeleteChild>> " + serviceRef.getKey());
        final String endPath = end;
        serviceRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e(TAG,"onDeleteChild listner >> " + dataSnapshot.getKey());
                for (DataSnapshot currChild : dataSnapshot.getChildren()) {
                    Log.e(TAG, "^Ben-currChild >>" + currChild.getKey());
                    Log.e(TAG, "^Ben-endPath >>" + endPath);

                    Option op = currChild.getValue(Option.class);
                    Log.e(TAG, "^Ben-op >>" + op);
                    //if (currChild.child("name").getValue(String.class).contains(endPath))
                    //{
                    // serviceRef.setValue(null);
                    //}
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    interface onAdminsFetchedListener {
        void onAdminsFetched(boolean i_isAdmin);
    }

    interface onProblemsFetchedListener {
        public void OnProblemsFetchedListener(HashMap<String, HashMap<String, MenuProblem>> i_Problems);
    }

    interface onPrivacyPolicyFetchedListener {
        public void OnPrivacyPolicyFetched(boolean i_privacyPolicy);
    }

    interface onFavoritesServicesFetchedListener {
        public void OnFavoritesServicesFetched(Map<String, ServiceItem> i_favoritesServicesArray);
    }

    interface onLastCallFetchedListener {
        public void OnLastCallFetched(String i_userLastCall, String i_userLastCallDial);
    }

    interface OnAttributeFetchedListener {
        public void OnAtttibuteFetch(String i_attributeValue);
    }

    interface OnUserFetchedByEmailListener {
        public void OnUserFetchedByEmail(User i_user, String i_userUID);
    }
    interface OnUserFetchedListener {
        public void OnUserFetch(User i_user);
    }

    interface OnOptionFetchedListener {
        public void OnMenuFetch(Option i_opt, ServiceItem i_service);
    }

    interface OnServicesFetchedListener {
        public void OnServicesFetched(Map<String, ArrayList<ServiceItem>> i_servicesData);
    }

    public void SetOnAdminsFetchedListener(onAdminsFetchedListener iOnAdminsFetchedListener) {
        mOnAdminsFetchedListener = iOnAdminsFetchedListener;
    }

    public void SetOnPrivacyPolicyFetchedListener(onPrivacyPolicyFetchedListener iOnPrivacyPolicyFetchedListener) {
        mOnPrivacyPolicyFetchedListener = iOnPrivacyPolicyFetchedListener;
    }

    public void SetonProblemsFetchedListener(onProblemsFetchedListener iOnProblemsFetchedListener) {
        mOnProblemsFetchedListener = iOnProblemsFetchedListener;
    }

    public void removePrivacyListener() {
        mOnPrivacyPolicyFetchedListener = null;
    }


    public void SetOnFavoritesServicesFetchedListener(onFavoritesServicesFetchedListener iOnFavoritesServicesFetchedListener) {
        mOnFavoritesServicesFetchedListener = iOnFavoritesServicesFetchedListener;
    }

    public void SetOnLastCallFetchedListener(onLastCallFetchedListener ionLastCallFetchedListener) {
        mOnLastCallFetcherListener = ionLastCallFetchedListener;
    }

    public void SetOnAttributeFetchedListener(OnAttributeFetchedListener iOnAttributeFetchedListener) {
        mOnAttributeFetcherListener = iOnAttributeFetchedListener;
    }

    public void SetOnUserFetchedByEmailListener(OnUserFetchedByEmailListener iOnUserFetchedByEmailListener) {
        mOnUserFetchedByEmailListener = iOnUserFetchedByEmailListener;
    }

    public void SetOnUserFetchedListener(OnUserFetchedListener iOnUserFetchedListener) {
        mOnUserFetcherListener = iOnUserFetchedListener;
    }

    public void SetOnOptionFetchedListener(OnOptionFetchedListener iOnOptionFetchedListener) {
        mOnOptionFetcherListener = iOnOptionFetchedListener;
    }

    public void SetOnServicesFetchedListener(OnServicesFetchedListener iOnServicesFetchedListener) {
        mOnServicesFetchedListener = iOnServicesFetchedListener;
    }

    public ServerHandler() {
        mUser = new User();

    }

    private Option extractMenuTree(DataSnapshot i_dataSnapshot, Option i_menuTree) {
        if (i_dataSnapshot.hasChild("subMenu")) {
            for (DataSnapshot snap : i_dataSnapshot.child("subMenu").getChildren()) {
                Option option = null;
                String currType = snap.child("type").getValue(String.class);
                switch (currType) {

                    case "DataOption": {

                        option = snap.getValue(DataOption.class);
                        Log.e(TAG, "entered to If: Name - " + option.getName() + "Type -" + option.getType() + "dataType: " + ((DataOption) option).getDataType());

                        break;
                    }
                    case "Option": {
                        option = snap.getValue(Option.class);
                        Log.e(TAG, "entered to If: Name - " + option.getName() + "Type -" + option.getType());
                        break;
                    }

                }

                i_menuTree.getSubMenu().add(extractMenuTree(snap, option));
            }
        }
        return i_menuTree;

    }

    private String getMenuRef(String i_MenuName) {
        return "Menus/" + i_MenuName + "/Menu";
    }

    public void writeNewService(Option i_menu, ServiceItem service) {
        Log.e(TAG, "onWriteMenu >>");
        DatabaseReference writeMenuRef;

        //write option
        writeMenuRef = FirebaseDatabase.getInstance().getReference("Menus/" + service.getM_name());
        writeMenuRef.child("Menu").setValue(i_menu);
        //write avatar
        writeMenuRef.child("Avatar").setValue(service.getM_avatar());
        //write genre
        writeMenuRef.child("Genre").setValue(service.getM_genre());

        Log.e(TAG, "onWriteMenu <<");
    }

    public void fetchMenu(String i_menuName) {

        Log.e(TAG, "onFetchFromServer >>");
        DatabaseReference serviceRef;
        serviceRef = FirebaseDatabase.getInstance().getReference("Menus/" + i_menuName);
        serviceRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e(TAG, "onDataChanged >>");
                Option menuTree = new Option();

                ServiceItem serviceItem = new ServiceItem();
                serviceItem.setM_name(dataSnapshot.getKey());
                serviceItem.setM_avatar(dataSnapshot.child("Avatar").getValue(String.class));
                serviceItem.setM_genre(dataSnapshot.child("Genre").getValue(String.class));

                //belongs to newer version of extract
                menuTree = dataSnapshot.child("Menu").getValue(Option.class);
                //end of newer version

                menuTree = extractMenuTree(dataSnapshot.child("Menu"), menuTree);

                Log.e(TAG, "onDataChanged <<");
                if (mOnOptionFetcherListener != null)
                    mOnOptionFetcherListener.OnMenuFetch(menuTree, serviceItem);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancel >>");
                Log.e(TAG, "onCancel <<");
            }
        });
        Log.e(TAG, "onFetchFromServer <<");
    }

    public void fetchServices() {

        DatabaseReference allMenuRef = FirebaseDatabase.getInstance().getReference("Menus");
        allMenuRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //get all menus data
                Map<String, ArrayList<ServiceItem>> categorizedDataMap = new HashMap<>();
                ArrayList<ServiceItem> servicesData = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    ServiceItem serviceItem = new ServiceItem();
                    serviceItem.setM_name(snapshot.getKey());
                    serviceItem.setM_avatar(snapshot.child("Avatar").getValue(String.class));
                    serviceItem.setM_genre(snapshot.child("Genre").getValue(String.class));
                    servicesData.add(serviceItem);
                    if (!categorizedDataMap.containsKey(serviceItem.getM_genre())) {
                        categorizedDataMap.put(serviceItem.getM_genre(), new ArrayList<ServiceItem>());

                    }
                    categorizedDataMap.get(serviceItem.getM_genre()).add(serviceItem);
                    //call adapterOfRecyclerView(servicesData);
                }
                categorizedDataMap.put("הכל", servicesData);
                if (mOnServicesFetchedListener != null)
                    mOnServicesFetchedListener.OnServicesFetched(categorizedDataMap);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void writeUser(final User user) {
        Log.e(TAG, user.toString());
        fbUsr = FirebaseAuth.getInstance().getCurrentUser();
        Log.e(TAG, "id is: " + fbUsr.getUid());
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users/" + fbUsr.getUid() + "/UserObject");
        if (user != null)
            userRef.setValue(user);
        //userRef.child("PrivacyPolicy").setValue("true");


    }

    public void fetchUser(String i_userID) {
        /*if (IsUserLogedIn()){
            return;
        }*/
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(i_userID);
        userRef.child("UserObject").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    Log.e(TAG, "inUserFetch" + user.toString());

                    if (mOnUserFetcherListener != null)
                        mOnUserFetcherListener.OnUserFetch(user);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        userRef = FirebaseDatabase.getInstance().getReference("Users/" + i_userID + "/UserObject");
        userRef.child("m_PrivacyPolicy").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Boolean res = (Boolean) dataSnapshot.getValue();

                Log.e(TAG, "res " + res);
                mUser.SetPrivacyPolicy(res);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void fetchUserAttribute(final String i_userAttribute) {
        fbUsr = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users/" + fbUsr.getUid());
        DatabaseReference userDataRef = userRef.child("User Data");
        userDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (mOnAttributeFetcherListener != null && dataSnapshot.child(i_userAttribute).getValue() != null) {
                    String userAttribute = dataSnapshot.child(i_userAttribute).getValue().toString();
                    mOnAttributeFetcherListener.OnAtttibuteFetch(userAttribute);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void writeUserAttribute(String i_userAttribute, String i_attributeValue) {
        fbUsr = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users/" + fbUsr.getUid());
        userRef.child("User Data").child(i_userAttribute).setValue(i_attributeValue);
    }

    public void writeUserLastCall(String i_pathName, String i_pathCall) {
        fbUsr = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users/" + fbUsr.getUid() + "/LastCall");
        userRef.child("lastCallPathName").setValue(i_pathName);
        userRef.child("lastCallPathCall").setValue(i_pathCall);
    }

    public void fetchUserLastCall() {
        fbUsr = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users/" + fbUsr.getUid() + "/LastCall");
        //DatabaseReference userDataRef = userRef.child("lastCallPathName");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                String lastCallDial = dataSnapshot.child("lastCallPathCall").getValue(String.class);
                String lastCallString = dataSnapshot.child("lastCallPathName").getValue(String.class);
                if (lastCallString != null)
                    if (mOnLastCallFetcherListener != null)
                        mOnLastCallFetcherListener.OnLastCallFetched(lastCallString, lastCallDial);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void writeUserFavoriteServices(Map<String, ServiceItem> i_userFavoritesServices) {
        fbUsr = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users/" + fbUsr.getUid());
        userRef.child("Favorites").setValue(i_userFavoritesServices);
    }

    public void fetchUserFavoritesServices() {
        fbUsr = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users/" + fbUsr.getUid());
        DatabaseReference userDataRef = userRef.child("Favorites");
        userDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, ServiceItem> servicesDataMap = new HashMap<>();

                int i = 1;
                String index;
                while (true) {
                    index = String.valueOf(i);
                    if (dataSnapshot.hasChild(index)) {
                        ServiceItem serviceItem = new ServiceItem();
                        serviceItem = dataSnapshot.child(index).getValue(ServiceItem.class);
                        servicesDataMap.put(index, serviceItem);
                        i++;
                    } else {
                        break;
                    }

                }

                if (mOnFavoritesServicesFetchedListener != null)
                    mOnFavoritesServicesFetchedListener.OnFavoritesServicesFetched(servicesDataMap);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void onPrivacyPolicySwitchedOff() {
        fbUsr = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users/" + fbUsr.getUid());
        userRef.child("User Data").setValue(null);
        userRef.child("UserObject").child("m_PrivacyPolicy").setValue(false);

    }

    public void onPrivacyPolicySwitchedOn() {
        fbUsr = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users/" + fbUsr.getUid() + "/UserObject");
        userRef.child("m_PrivacyPolicy").setValue(true);
    }

    public void fetchUserPrivacyPolicy() {
        fbUsr = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users/" + fbUsr.getUid() + "/UserObject");
        userRef.child("m_PrivacyPolicy").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String privacyPolicy = dataSnapshot.getValue(String.class);


                if (mOnPrivacyPolicyFetchedListener != null) {
                    if (privacyPolicy != null)
                        mOnPrivacyPolicyFetchedListener.OnPrivacyPolicyFetched(privacyPolicy.equals("true"));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void writeAProblem(MenuProblem i_menuProblem, User reporterUser, boolean isAdmin) {
        String uID;
        if (!isAdmin) {
            fbUsr = FirebaseAuth.getInstance().getCurrentUser();
            i_menuProblem.setmUserEmail(fbUsr.getEmail());
            uID = fbUsr.getUid();
        } else {
            i_menuProblem.setmUserEmail(reporterUser.getM_email());
            uID = reporterUser.getM_ID();
        }
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Reviews/" + i_menuProblem.getmServiceName() + "/" + uID);
        userRef.setValue(i_menuProblem);
    }

    public void fetchProblems() {
        Log.e(TAG, "onfetchProblems >>");

        fbUsr = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users/");
        DatabaseReference reviewsRef = FirebaseDatabase.getInstance().getReference("Reviews/");
        reviewsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e(TAG, "onFetchProblemsEventListener >>");
                HashMap<String, HashMap<String, MenuProblem>> problems = new HashMap();
                HashMap<String, MenuProblem> currUserAndMenu = new HashMap<>();
                MenuProblem menuProblem = new MenuProblem();

                for (DataSnapshot snaptService : dataSnapshot.getChildren()) {
                    for (DataSnapshot snapUserAndMenu : snaptService.getChildren()) {
                        String userID = snapUserAndMenu.getKey();
                        menuProblem = snapUserAndMenu.getValue(MenuProblem.class);
                        currUserAndMenu.put(userID, menuProblem);
                    }
                    problems.put(snaptService.getKey(), currUserAndMenu);
                    currUserAndMenu = new HashMap<>();
                }

                if (mOnProblemsFetchedListener != null) {
                    if (problems != null)
                        mOnProblemsFetchedListener.OnProblemsFetchedListener(problems);
                }
                Log.e(TAG, "onFetchProblemsEventListener <<");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Log.e(TAG, "onfetchProblems <<");
    }

    public boolean IsUserLogedIn() {
        return (FirebaseAuth.getInstance().getCurrentUser() != null);
    }

    /*public void fetchAdmins() {
        final ArrayList<String> adminsArray = new ArrayList();
        DatabaseReference admins = FirebaseDatabase.getInstance().getReference("Admins");
        admins.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    String admin = snap.getValue(String.class);
                    Log.e(TAG, "admin is : " + admin);
                    adminsArray.add(admin);
                }
                if (mOnAdminsFetchedListener != null)
                    mOnAdminsFetchedListener.onAdminsFetched(adminsArray);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }*/

    public void addPointsToUser(int i_points) {
        fbUsr = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users/" + fbUsr.getUid() + "/UserObject");
        userRef.child("m_points").setValue(i_points);

    }

    public void writeAdmin(String i_adminMail) {
        fbUsr = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Admins/" + fbUsr.getUid());
        userRef.setValue(i_adminMail);

    }

    public void IsAdmin(final String i_adminUid) {


        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Admins");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snap: dataSnapshot.getChildren())
                {
                    String currentAdmin = snap.getValue(String.class);
                    Log.e(TAG,"admins: "+currentAdmin + "curr "+i_adminUid);
                    if (currentAdmin.equals(i_adminUid))
                        if (mOnAdminsFetchedListener!=null)
                            mOnAdminsFetchedListener.onAdminsFetched(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void fetchUserByEmail(String i_Email) {
        final String currEmail = i_Email;
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user;
                String userUID;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("UserObject/m_email").getValue(String.class).equals(currEmail)) {
                        user = data.child("UserObject").getValue(User.class);
                        userUID = data.getKey();
                        if (mOnUserFetchedByEmailListener != null)
                            mOnUserFetchedByEmailListener.OnUserFetchedByEmail(user, userUID);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}