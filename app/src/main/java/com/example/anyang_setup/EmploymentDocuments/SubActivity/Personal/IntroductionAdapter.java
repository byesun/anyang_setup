package com.example.anyang_setup.EmploymentDocuments.SubActivity.Personal;

// IntroductionAdapter.java
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anyang_setup.R;

import java.util.ArrayList;
import java.util.List;

public class IntroductionAdapter extends RecyclerView.Adapter<IntroductionAdapter.IntroductionViewHolder> implements Filterable {

    private List<String> introductionList;
    private List<String> introductionListFull;

    public IntroductionAdapter(List<String> introductionList) {
        this.introductionList = introductionList;
        this.introductionListFull = new ArrayList<>(introductionList);
    }

    @Override
    public IntroductionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.personal_introduction_item, parent, false);
        return new IntroductionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IntroductionViewHolder holder, int position) {
        String introduction = introductionList.get(position);
        holder.titleTextView.setText(introduction);
    }

    @Override
    public int getItemCount() {
        return introductionList.size();
    }

    @Override
    public Filter getFilter() {
        return introductionFilter;
    }

    private Filter introductionFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<String> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(introductionListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (String item : introductionListFull) {
                    if (item.toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            introductionList.clear();
            introductionList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public static class IntroductionViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;

        public IntroductionViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
        }
    }

    public static class PersonalEditActivity extends AppCompatActivity {

        String selectedText;
        EditText personal_edit;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_personal_edit);

            Intent intent = getIntent();
            if (intent != null) {
                selectedText = intent.getStringExtra("selectedText");
            }
            personal_edit = findViewById(R.id.personal_edit);
            personal_edit.setText(selectedText);

            Button Personal_Edit_button = findViewById(R.id.personal_out_button);
            Personal_Edit_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showExitDialog();
                }
            });

            Button Personal_Save_button = findViewById(R.id.personal_save_button);
            Personal_Save_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


        }

        //뒤로가기버튼 처리
        @Override
        public void onBackPressed() {
            new AlertDialog.Builder(this)
                    .setMessage("수정한내역이 저장되지않았습니다. 나가시겠습니까?")
                    .setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(PersonalEditActivity.this, PersonalLockerActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // "아니요" 버튼이 눌렸을 때 다이얼로그를 닫음
                            dialog.dismiss();
                        }
                    })
                    .show();
        }

        // 다이얼로그를 표시하는 메소드
        private void showExitDialog() {
            new AlertDialog.Builder(this)
                    .setMessage("수정한내역이 저장되지않았습니다. 나가시겠습니까?")
                    .setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(PersonalEditActivity.this, PersonalLockerActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // "아니요" 버튼이 눌렸을 때 다이얼로그를 닫음
                            dialog.dismiss();
                        }
                    })
                    .show();
        }

        // 수정한내역을 저장하는 메소드

    }
}
