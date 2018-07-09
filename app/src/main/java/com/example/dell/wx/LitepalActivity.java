package com.example.dell.wx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import org.litepal.LitePal;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LitepalActivity extends AppCompatActivity {

    @BindView(R.id.LitepalCreate)
    Button LitepalCreate;
    @BindView(R.id.LitepalAdd)
    Button LitepalAdd;
    @BindView(R.id.LitepalUpdate)
    Button LitepalUpdate;
    @BindView(R.id.LitepalDelete)
    Button LitepalDelete;
    @BindView(R.id.LitepalQuery)
    Button LitepalQuery;
    @BindView(R.id.textView)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_litepal);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.LitepalCreate)
    public void onLitepalCreateClicked() {
        LitePal.getDatabase();
    }

    @OnClick(R.id.LitepalAdd)
    public void onLitepalAddClicked() {
        Book book = new Book();
        book.setName("android");
        book.setPrice(19.99);
        book.setName("delamey");
        book.setPages(500);
        book.setPress("ncut");
        book.save();
        Book book1 = new Book();
        book1.setName("python");
        book1.setPrice(22.22);
        book1.setName("delamey");
        book1.setPages(452);
        book1.setPress("optas");
        book1.save();
    }

    @OnClick(R.id.LitepalUpdate)
    public void onLitepalUpdateClicked() {
        Book book = new Book();
        book.setPrice(33.33);
        book.setAuthor("opt");
        book.updateAll("name=? and author=?", "python", "delamey");
    }

    @OnClick(R.id.LitepalDelete)
    public void onLitepalDeleteClicked() {
        LitePal.deleteAll(Book.class, "price<?", "20");
    }

    @OnClick(R.id.LitepalQuery)
    public void onLitepalQueryClicked() {
        List<Book> list = LitePal.select("name", "price").find(Book.class);
        for (Book book:list)
        {
         textView.append(book.getName());
         textView.append(" ");
         textView.append(String.valueOf(book.getPrice()));
         textView.append("\r\n");
        }
    }
}
