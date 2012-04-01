package com.home.lepradroid;

import java.util.ArrayList;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.lepradroid.objects.BaseItem;
import com.home.lepradroid.objects.Blog;
import com.home.lepradroid.utils.ImageLoader;
import com.home.lepradroid.utils.Utils;

class BlogsAdapter extends ArrayAdapter<BaseItem>
{
    private ArrayList<BaseItem> posts = new ArrayList<BaseItem>();
    public ImageLoader          imageLoader;
            
    public BlogsAdapter(Context context, int textViewResourceId,
            ArrayList<BaseItem> posts)
    {
        super(context, textViewResourceId, posts);
        this.posts = posts;
        imageLoader=new ImageLoader(LepraDroidApplication.getInstance());
    }

    public int getCount() 
    {
        return posts.size();
    }
    
    public BaseItem getItem(int position) 
    {
        return posts.get(position);
    }
    
    public long getItemId(int position) 
    { 
        return position;
    }
    
    public void updateData(ArrayList<BaseItem> posts)
    {
        this.posts = posts;
    }
    
    public void addProgressElement()
    {
        synchronized(posts)
        {
            if(!posts.isEmpty() && posts.get(posts.size() - 1) != null)
                posts.add(null);   
        }
    }
    
    public boolean isContainProgressElement()
    {
        synchronized(posts)
        {
            if(!posts.isEmpty() && posts.get(posts.size() - 1) == null)
                return true;
            else
                return false;
        }
    }
    
    public void removeProgressElement()
    {
        synchronized(posts)
        {
            if(!posts.isEmpty() && posts.get(posts.size() - 1) == null)
                posts.remove(null);
        }
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) 
    {
        Blog blog = (Blog)getItem(position);
        LayoutInflater aInflater=LayoutInflater.from(getContext());
        
        if(blog != null)
        {
            View view = aInflater.inflate(R.layout.blog_row_view, parent, false);
            
            TextView text = (TextView)view.findViewById(R.id.text);
            text.setText(Utils.html2text(blog.Html));
            Utils.setTextViewFontSize(getContext(), text);
            
            TextView author = (TextView)view.findViewById(R.id.author);
            author.setText(Html.fromHtml(blog.Signature));
            Utils.setTextViewFontSize(getContext(), author);

            TextView stat = (TextView)view.findViewById(R.id.stat);
            stat.setText(Html.fromHtml(blog.Stat));
            Utils.setTextViewFontSize(getContext(), stat);
            
            ImageView image = (ImageView)view.findViewById(R.id.image);
            
            if(!TextUtils.isEmpty(blog.ImageUrl))
            {
                image.setVisibility(View.VISIBLE);
                
                imageLoader.DisplayImage(blog.ImageUrl, image);
            }
            
            return view;
        }
        else
        {
            return (View) aInflater.inflate(R.layout.footer_view, null);
        }
    }
}
