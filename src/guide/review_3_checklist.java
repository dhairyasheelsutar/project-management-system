package guide;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Database;
import models.Functions;

@WebServlet("/review_3_checklist")
public class review_3_checklist extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public review_3_checklist() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	

		Database database = new Database();
		Functions functions = new Functions();
		
		String[] values = new String[7];
		String grpid = request.getParameter("grpid");
		
		int j=0;
		for(int i=32;i<39;i++) {
			values[i-32] = request.getParameter("check1list" + i);
		}
		
		//update query construction
		
		j=0;
		String query = "update checklist set grpid" + grpid + " = ( case";
		for(int i=32;i<39;i++) {
			j = i+1;
			query += " when id = " + j + " then " + values[i-32];
		}
		query += " end ) where type = 3";
		String output = "";
		
		
		System.out.println(query);
		if(database.dml(query) != -1) {
			output = functions.set_output_message("Review 3 checklist updated", true);
		}else {
			output = functions.set_output_message("Something went wrong", false);
		}
		
		request.setAttribute("review_result", output);
		
		//forward to servlet with grpid (imp).
		
		request.getRequestDispatcher("student_group_single_guide?grpid=" + grpid).forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
