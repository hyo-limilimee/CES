package com.android.buddy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.ClipData;
import android.net.Uri;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.QuerySnapshot;


public class CalendarInfo extends AppCompatActivity {
    private  FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String uid;

    ImageView photo;
    private FirebaseStorage storage;

    private static final String TAG = "CalendarInfo";
    ArrayList<Uri> uriList = new ArrayList<>();
    ArrayList<Model> list = new ArrayList<>();
    UriAdapter uriAdapter;

    RecyclerView recyclerView;
    MultiImageAdapter adapter;

    public String fname = null;
    public String str = null;

    EditText mMemoEdit = null;
    TextFileManager mTextFileManager = new TextFileManager(this);

    TextView TextDate;

    EditText editLocation;
    TextView textLocation;
    TextView friend_1;
    TextView friend_2;
    TextView friend_3;
    TextView friend_4;
    EditText memo_edit;
    TextView memo_text;
    ImageView changeLocation;
    ImageView changeMemo;
    Long friend_num;
    String friendName;
    int imageNum;
    int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_info);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        storage=FirebaseStorage.getInstance();
        uid = user.getUid();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        uriAdapter = new UriAdapter(CalendarInfo.this, list);

        Intent intent = getIntent();
        String date = intent.getStringExtra("DATE");

        TextDate = (TextView) findViewById(R.id.TextDate);
        TextDate.setText(date);


        /*
        db.collection("users").document(uid).collection("calendar").document(TextDate.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        if(document.getString("location") != null)
                            setLocation(document.getString("location"));
                        if(document.getString("memo") != null)
                            setMemo(document.getString("memo"));
                        setImage();
                        setFriend(document.getString("friend1"),document.getString("friend2"),document.getString("friend3"),document.getString("friend4"));
                    } else {
                        Map<String, Object> dateInfo = new HashMap<>();
                        dateInfo.put("date", TextDate.getText().toString());
                        dateInfo.put("location",null);
                        dateInfo.put("memo", null);
                        dateInfo.put("friend1", null);
                        dateInfo.put("friend2", null);
                        dateInfo.put("friend3", null);
                        dateInfo.put("friend4", null);
                        dateInfo.put("friend_num", 0);

                        db.collection("users").document(uid).collection("calendar").document(TextDate.getText().toString()).set(dateInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                            }
                        });
                    }
                }
            }
        }); */


        db.collection("users").document(uid).collection("calendar").document(TextDate.getText().toString()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    if(snapshot.getString("location") != null)
                        setLocation(snapshot.getString("location"));
                    if(snapshot.getString("memo") != null)
                        setMemo(snapshot.getString("memo"));
                    setImage();
                    setFriend(snapshot.getString("friend1"),snapshot.getString("friend2"),snapshot.getString("friend3"),snapshot.getString("friend4"));
                } else {
                    Map<String, Object> dateInfo = new HashMap<>();
                    dateInfo.put("date", TextDate.getText().toString());
                    dateInfo.put("location",null);
                    dateInfo.put("memo", null);
                    dateInfo.put("friend1", null);
                    dateInfo.put("friend2", null);
                    dateInfo.put("friend3", null);
                    dateInfo.put("friend4", null);
                    dateInfo.put("friend_num", 0);

                    db.collection("users").document(uid).collection("calendar").document(TextDate.getText().toString()).set(dateInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                        }
                    });
                }
            }
        });

        editLocation = findViewById(R.id.TextLocationData);
        textLocation = findViewById(R.id.LocationData);
        friend_1 = findViewById(R.id.TextFriendName01);
        friend_2 = findViewById(R.id.TextfriendName02);
        friend_3 = findViewById(R.id.TextfriendName03);
        friend_4 = findViewById(R.id.TextfriendName04);
        memo_edit = findViewById(R.id.memo_edit);
        memo_text = findViewById(R.id.memo_text);
        changeLocation = findViewById(R.id.changeLocation);
        changeMemo = findViewById((R.id.changeMemo));

        changeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(textLocation.getVisibility()==View.VISIBLE) {
                    editLocation.setVisibility(View.VISIBLE);
                    textLocation.setVisibility(View.GONE);
                }
                if(TextUtils.isEmpty(editLocation.getText().toString())) {
                    Toast.makeText (CalendarInfo.this, "장소를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    String location = editLocation.getText().toString();
                    editLocation.setText(null);
                    updateLocation(location);
                }
            }
        });
        changeMemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(memo_text.getVisibility()==View.VISIBLE) {
                    memo_edit.setVisibility(View.VISIBLE);
                    memo_text.setVisibility(View.GONE);
                }
                if(TextUtils.isEmpty(memo_edit.getText().toString())) {
                    Toast.makeText (CalendarInfo.this, "글을 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    String memo = memo_edit.getText().toString();
                    memo_edit.setText(null);
                    updateMemo(memo);
                }
            }
        });

        friend_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText (CalendarInfo.this, "일정에 등록할 친구를 선택하세요.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CalendarInfo.this, FriendList.class);
                intent.putExtra("Date", TextDate.getText().toString());
                startActivity(intent);
            }
        });
        friend_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText (CalendarInfo.this, "일정에 등록할 친구를 선택하세요.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CalendarInfo.this, FriendList.class);
                intent.putExtra("Date", TextDate.getText().toString());
                startActivity(intent);
            }
        });
        friend_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText (CalendarInfo.this, "일정에 등록할 친구를 선택하세요.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CalendarInfo.this, FriendList.class);
                intent.putExtra("Date", TextDate.getText().toString());
                startActivity(intent);
            }
        });
        friend_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText (CalendarInfo.this, "일정에 등록할 친구를 선택하세요.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CalendarInfo.this, FriendList.class);
                intent.putExtra("Date", TextDate.getText().toString());
                startActivity(intent);
            }
        });

        // 앨범으로 이동하는 버튼
        Button btn_getImage = findViewById(R.id.getImage);
        btn_getImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2222);
            }
        });
    }

    @Override
    public void onRestart(){
        super.onRestart();
        db.collection("users").document(uid).collection("calendar").document(TextDate.getText().toString()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    if(snapshot.getString("location") != null)
                        setLocation(snapshot.getString("location"));
                    if(snapshot.getString("memo") != null)
                        setMemo(snapshot.getString("memo"));
                    setImage();
                    setFriend(snapshot.getString("friend1"),snapshot.getString("friend2"),snapshot.getString("friend3"),snapshot.getString("friend4"));
                }
            }
        });
    }

    // 앨범에서 액티비티로 돌아온 후 실행되는 메서드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) {   // 어떤 이미지도 선택하지 않은 경우
            Toast.makeText(getApplicationContext(), "이미지를 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
        } else {   // 이미지를 하나라도 선택한 경우
            if (data.getClipData() == null) {     // 이미지를 하나만 선택한 경우
                Log.e("single choice: ", String.valueOf(data.getData()));
                Uri imageUri = data.getData();
                uriList.add(imageUri);

                adapter = new MultiImageAdapter(uriList, getApplicationContext());
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
            } else {      // 이미지를 여러장 선택한 경우
                ClipData clipData = data.getClipData();
                Log.e("clipData", String.valueOf(clipData.getItemCount()));

                if (clipData.getItemCount() > 10) {   // 선택한 이미지가 11장 이상인 경우
                    Toast.makeText(getApplicationContext(), "사진은 10장까지 선택 가능합니다.", Toast.LENGTH_LONG).show();
                } else {   // 선택한 이미지가 1장 이상 10장 이하인 경우
                    Log.e(TAG, "multiple choice");

                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        num = i;
                        Uri imageUri = clipData.getItemAt(i).getUri();  // 선택한 이미지들의 uri를 가져온다.
                        //uploadFireBase(i,imageUri);
                        StorageReference storageReference = storage.getReference();
                        StorageReference riversRef = storageReference.child("photo/"+uid+"/"+i+".png");
                        UploadTask uploadTask = riversRef.putFile(imageUri);

                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(CalendarInfo.this, "사진 업로드 실패", Toast.LENGTH_LONG).show();
                            }
                        });

                        Map<String, Object> uriInfo = new HashMap<>();
                        uriInfo.put("date", TextDate.getText().toString());
                        uriInfo.put("imageUri", imageUri.toString());

                        db.collection("users").document(uid).collection("Image").document(TextDate.getText().toString()+"."+String.valueOf(i)).set(uriInfo);
                        try {
                            uriList.add(imageUri);  //uri를 list에 담는다.

                        } catch (Exception e) {
                            Log.e(TAG, "File select error", e);
                        }
                    }

                    adapter = new MultiImageAdapter(uriList, getApplicationContext());
                    recyclerView.setAdapter(adapter);   // 리사이클러뷰에 어댑터 세팅
                    recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));     // 리사이클러뷰 수평 스크롤 적용
                }
            }
        }
    }

    public void setLocation (String location) {
        textLocation.setText(location);

        editLocation.setVisibility(View.GONE);
        textLocation.setVisibility(View.VISIBLE);
    }
    public void setMemo (String memo) {
        memo_text.setText((memo));

        memo_edit.setVisibility(View.GONE);
        memo_text.setVisibility(View.VISIBLE);
    }
    public void setImage() {
        recyclerView.setAdapter(uriAdapter);
        db.collection("users").document(uid).collection("Image").whereEqualTo("date", TextDate.getText().toString()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Log.e("Firestore error", error.getMessage());
                    return;
                }
                for(DocumentChange dc : value.getDocumentChanges()) {
                    if(dc.getType() == DocumentChange.Type.ADDED) {
                        list.add(dc.getDocument().toObject(Model.class));
                    }

                    uriAdapter.notifyDataSetChanged();

                }
            }
        });
    }
    public void setFriend(String friend1, String friend2, String friend3, String friend4) {
        if(friend1 != null) {
            friend_1.setText(friend1);
        }
        if(friend2 != null) {
            friend_2.setText(friend2);
        }
        if(friend3 != null) {
            friend_3.setText(friend3);
        }
        if(friend4 != null) {
            friend_4.setText(friend4);
        }
    }
    public void updateLocation(String location) {
        db.collection("users").document(uid).collection("calendar").document(TextDate.getText().toString()).update("location", location).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText (CalendarInfo.this, "장소 등록 완료", Toast.LENGTH_SHORT).show();
            }
        });
        textLocation.setText(location);
        textLocation.setVisibility(View.VISIBLE);
        editLocation.setVisibility(View.GONE);
    }

    public void updateMemo(String memo) {
        db.collection("users").document(uid).collection("calendar").document(TextDate.getText().toString()).update("memo", memo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText (CalendarInfo.this, "글 등록 완료", Toast.LENGTH_SHORT).show();
            }
        });
        memo_text.setText(memo);
        memo_edit.setVisibility(View.GONE);
        memo_text.setVisibility(View.VISIBLE);
    }

}