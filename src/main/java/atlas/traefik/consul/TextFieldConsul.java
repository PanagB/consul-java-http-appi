package atlas.traefik.consul;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;


import java.util.logging.Logger;


public class TextFieldConsul implements ActionListener{
	
	 private static String URL="http://127.0.0.1:8500/v1/kv/";
     
	 Image img;
	 JTextField tf1,tf2,tf3;  
	 JButton b1,b2;  
	 static Logger logger  = Logger.getLogger(TextFieldConsul.class.getName());   
	       
	    TextFieldConsul(){  
	        JFrame f= new JFrame("Atlas-Consul");  
	        tf1=new JTextField("key");  
	        tf1.setBounds(243,50,300,20);  
	        tf2=new JTextField("value");  
	        tf2.setBounds(243,100,300,20);  
	        tf3=new JTextField();  
	        tf3.setBounds(243,150,300,20);  
	        tf3.setEditable(false);   
	        b1=new JButton("PUT");  
	        b1.setBounds(280,200,100,30);  
	        b1.addActionListener(this); 
	        b2=new JButton("DELETE");
	        b2.setBounds(420,200,100,30);
	        b2.addActionListener(this);
	        f.getContentPane().setBackground( Color.black );
	        f.add(tf1);f.add(tf2);f.add(tf3);f.add(b1);f.add(b2);  
	        f.setSize(800,800);  
	        f.setLayout(null);  
	        f.setVisible(true);  
	    }
	    public void actionPerformed(ActionEvent e){ 
	        String key=tf1.getText();  
	        String value=tf2.getText();  
	        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
	       if(e.getSource()==b1) {
	    	   String url = URL + key;
	    	   if (!url.contains(" ")) {
	    		   HttpPut request = new HttpPut(URL+key);
	    	   try {
					StringEntity params = new StringEntity(value);
					request.setEntity(params);
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();	
				}
	    	   try {
					CloseableHttpResponse response = httpClient.execute(request);
					logger.info("Response is : " + response);
					int responseCode = response.getStatusLine().getStatusCode();
					tf3.setText(Integer.toString(responseCode));
					tf3.setBackground(Color.green);
					logger.info("You added the key : "+key); 
			        logger.info("You added the value : "+value);
					
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
	       }
	    	   else  {
	    		   tf3.setText("Key should not have any spaces...");
					tf3.setBackground(Color.red);
	    		   
	    	   }
	    	   }
	    	   
	       else if(e.getSource()==b2){ 
	    	   String url = URL + key;
	    	   if (!url.contains(" ")) {
	    		   HttpDelete request = new HttpDelete(URL+key);
	    		   try {
					CloseableHttpResponse response = httpClient.execute(request);
					logger.info("Response is : " + response);
					int responseCode = response.getStatusLine().getStatusCode();
					tf3.setText(Integer.toString(responseCode));
					tf3.setBackground(Color.green);
					logger.info("You deleted the key : "+key);
	    		   } catch (IOException e1) {
					
					e1.printStackTrace();
	    		   	}
	    	   }
	    	   
	    	   else  {
	    		   tf3.setText("Key should not have any spaces...");
				   tf3.setBackground(Color.red);
	    		   
	    	   }
	        }  

	        
	    }  
}
