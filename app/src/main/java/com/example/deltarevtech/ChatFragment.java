package com.example.deltarevtech;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat; // Fixed: Moved to top
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor; // Fixed: Moved to top

public class ChatFragment extends Fragment {

    private RecyclerView recyclerView;
    private EditText etMessage;
    private ImageButton btnSend;
    private List<Message> messageList;
    private MessageAdapter messageAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        recyclerView = view.findViewById(R.id.chat_recycler_view);
        etMessage = view.findViewById(R.id.et_message);
        btnSend = view.findViewById(R.id.btn_send);

        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList);

        recyclerView.setAdapter(messageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Brand Greeting
        if (messageList.isEmpty()) {
            addToChat("Hello! I am DeltaAI. Ask me anything about your studies!", Message.SENT_BY_AI);
        }

        btnSend.setOnClickListener(v -> {
            String question = etMessage.getText().toString().trim();
            if (!question.isEmpty()) {
                addToChat(question, Message.SENT_BY_USER);
                etMessage.setText("");
                callDeltaAI(question);
            }
        });

        return view;
    }

    void addToChat(String message, String sentBy) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                messageList.add(new Message(message, sentBy));
                messageAdapter.notifyItemInserted(messageList.size() - 1);
                recyclerView.smoothScrollToPosition(messageList.size() - 1);
            });
        }
    }

    void callDeltaAI(String question) {
        // 1. Setup the Gemini Model with your NEW key
        GenerativeModel gm = new GenerativeModel("gemini-1.5-flash", "AIzaSyBH1fqHh-WcgqOpgjTM7WcviHn92JCS-cg");
        GenerativeModelFutures model = GenerativeModelFutures.from(gm);

        Content content = new Content.Builder()
                .addText("You are DeltaAI, a helpful tutor. Question: " + question)
                .build();

        ListenableFuture<GenerateContentResponse> response = model.generateContent(content);

        // 2. Compatibility-safe Executor
        Executor mainExecutor = ContextCompat.getMainExecutor(requireContext());

        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(@NonNull GenerateContentResponse result) {
                String aiResponse = result.getText();
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        addToChat(aiResponse, Message.SENT_BY_AI);
                    });
                }
            }

            @Override
            public void onFailure(@NonNull Throwable t) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        // THIS LINE IS THE FIX: It will pop up the REAL error on your phone
                        android.widget.Toast.makeText(getContext(), "AI Error: " + t.getMessage(), android.widget.Toast.LENGTH_LONG).show();
                        addToChat("Connection Failed. See popup error.", Message.SENT_BY_AI);
                    });
                }
            }
        }, mainExecutor);


    }
}