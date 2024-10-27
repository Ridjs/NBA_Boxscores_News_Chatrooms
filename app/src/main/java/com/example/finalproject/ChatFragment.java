package com.example.finalproject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private Button createRoom;
    private Button joinRoom;
    private EditText roomCode;
    private LinearLayout layout;
    private ListView listViewChatMessages;
    private EditText editTextMessage;
    private Button buttonSend;
    private ArrayList<ChatMessage> messageList = new ArrayList<>();

    public ChatFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        createRoom = rootView.findViewById(R.id.btnCreate);
        joinRoom = rootView.findViewById(R.id.btnJoin);
        roomCode = rootView.findViewById(R.id.text_roomCode);
        listViewChatMessages = rootView.findViewById(R.id.list_chat_messages);
        editTextMessage = rootView.findViewById(R.id.edit_text_message);
        buttonSend = rootView.findViewById(R.id.btn_send);
        layout = rootView.findViewById(R.id.id_chatRoomActivity);
        createRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createChatRoom(roomCode.getText().toString().trim());
            }
        });
        joinRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinChatRoom(roomCode.getText().toString().trim());
            }
        });
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
        return rootView;
    }
    public void createChatRoom(String roomCode){
        String name = "PRIVATE ROOMS";
        String description = "CHAT ROOM";
        ChatRoom chatRoom = new ChatRoom(name, description);
        if(!roomCode.isEmpty()){
            databaseReference.child("chatRooms").child(roomCode).setValue(chatRoom);
            visibilityChanges();
            loadMessages();
        }
        else{
            this.roomCode.setError("ROOM CODE REQUIRED");
        }
    }
    public void joinChatRoom(String roomCode) {
        databaseReference.child("chatRooms").child(roomCode).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    visibilityChanges();
                    loadMessages();
                } else {
                    Toast.makeText(getContext(), "Room does not exist", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    private void sendMessage() {
        String messageText = editTextMessage.getText().toString().trim();
        if (!messageText.isEmpty()) {
            ChatMessage chatMessage = new ChatMessage(messageText, mAuth.getCurrentUser().getDisplayName());
            databaseReference.child("chatRooms").child(roomCode.getText().toString()).child("messages").push().setValue(chatMessage);
            editTextMessage.setText("");
            loadMessages();
        }
    }
    private void loadMessages() {
        DatabaseReference messagesRef = databaseReference.child("chatRooms").child(roomCode.getText().toString()).child("messages");
        messagesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messageList.clear();
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    ChatMessage message = messageSnapshot.getValue(ChatMessage.class);
                    messageList.add(message);
                }
                ChatMessageAdapter adapter = new ChatMessageAdapter(getContext(), mAuth, messageList);
                listViewChatMessages.setAdapter(adapter);
                listViewChatMessages.smoothScrollToPosition(messageList.size()-1);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    static class ChatRoom {
        private String name;
        private String description;
        public ChatRoom() {
            // Required empty public constructor
        }
        public ChatRoom(String name, String description) {
            this.name = name;
            this.description = description;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
        }
    }
    public static class ChatMessage {
        private String messageText;
        private String messageUser;
        public ChatMessage() {
            // Required empty public constructor
        }
        public ChatMessage(String messageText, String messageUser) {
            this.messageText = messageText;
            this.messageUser = messageUser;
        }
        public String getMessageText() {
            return messageText;
        }
        public void setMessageText(String messageText) {
            this.messageText = messageText;
        }
        public String getMessageUser() {
            return messageUser;
        }
        public void setMessageUser(String messageUser) {
            this.messageUser = messageUser;
        }

    }
    public void visibilityChanges(){
        createRoom.setVisibility(View.INVISIBLE);
        joinRoom.setVisibility(View.INVISIBLE);
        roomCode.setVisibility(View.INVISIBLE);
        layout.setVisibility(View.VISIBLE);
    }
    public static class ChatMessageAdapter extends ArrayAdapter<ChatMessage> {
        private final Context context;
        private final FirebaseAuth mAuth;
        private final ArrayList<ChatMessage> messages;

        public ChatMessageAdapter(Context context, FirebaseAuth mAuth,ArrayList<ChatMessage> messages) {
            super(context, 0, messages);
            this.context = context;
            this.mAuth = mAuth;
            this.messages = messages;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View listItem = layoutInflater.inflate(R.layout.list_item_message, null);
            ChatMessage currentMessage = messages.get(position);
            TextView messageText = listItem.findViewById(R.id.message_text);
            messageText.setText(currentMessage.getMessageText());
            TextView messageUser = listItem.findViewById(R.id.message_user);
            messageUser.setText(currentMessage.getMessageUser()+":");
            if(!currentMessage.getMessageUser().equals(mAuth.getCurrentUser().getDisplayName())){
                messageText.setGravity(Gravity.END);
                messageUser.setGravity(Gravity.END);
            }
            return listItem;
        }
    }
}