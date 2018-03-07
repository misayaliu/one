package jieyi.app.util.report;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.output.XMLOutputter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jieyi.app.form.report.t_report_base;
import jieyi.app.form.report.t_report_column;

public class ReportProducer {

	 protected Table table;
	 protected String header;
	 protected String footer;
	 protected String reportName;
	 protected String dateInfo;
	 protected String parametersStr;
	 protected String needFormat;
	 private static Namespace nsX = Namespace.getNamespace("x","http://www.bestpay.com.cn");
	 
	 
	 public ReportProducer(List<t_report_column> reportRpColumns,t_report_base reportRpBase,String[][] data,String dateInfo,String parametersStr) throws Exception{
		 TableProducer tableProducer=new TableProducer();
		 table=tableProducer.getTable(reportRpColumns, reportRpBase,data);
		 reportName=reportRpBase.getReport_name();
		 this.dateInfo=dateInfo;
		 this.parametersStr = parametersStr;
		 if(ReportConstant.NEED_YES.equals(reportRpBase.getNeed_batch_no())){
			 if(dateInfo!=null&&!dateInfo.equals("")){
				 String[] dateInfos=dateInfo.split("：");
				 String date=dateInfos[1].replaceAll("-","");
				 this.dateInfo+=" &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;报表批次号："+reportRpBase.getShort_eng()+date.substring(2,6)+"00"+date.substring(6,8);
			 }
			 
		 }
		 needFormat=reportRpBase.getNeed_format();
	 }
	 
	 
	 public String getQueryResult(){
	     this.table.setClassName("table table-striped table-bordered table-hover");
	 	 this.table.setAlign("center");
	 	 this.table.setBorder("1");
	 	 this.table.setWidth("95%");
	 	 this.table.setIdName("sample-table-1");
		 Element table=getTable();
		 String tableHtml=(new XMLOutputter()).outputString(table);
		 if(this.table.getThead()==null){
			 tableHtml=tableHtml.replace("<thead />",this.table.getTheadHtml());
		 }
		 return tableHtml;
	}
		
	public String getPrintWatchReport(){
	     this.table.setClassName("datalist");
	 	 this.table.setAlign("center");
	 	 this.table.setBorder("1");
	 	 this.table.setWidth("80%");
		 Element table=getTable();
		 String tableHtml=(new XMLOutputter()).outputString(table);
		 if(this.table.getThead()==null){
			 tableHtml=tableHtml.replace("<thead />",this.table.getTheadHtml());
		 }
		 StringBuffer sb=new StringBuffer();
		 sb.append(this.getReportName());
		 if(this.needFormat.equals(ReportConstant.REPORTRPCOLUMN_FORMAT_YES)){
			 sb.append(this.getTableHeader());
		 }
		 sb.append(tableHtml);
		 if(this.needFormat.equals(ReportConstant.REPORTRPCOLUMN_FORMAT_YES)){
			 sb.append(this.getTableFooter());
		 }
		 return sb.toString().replace("x:str=\"\"","x:str").replace("x:num=\"\"","x:num");
	} 
	 
	public String getExcelReport(){
	 	 this.table.setAlign("center");
	 	 this.table.setBorder("1");
	 	 this.table.setWidth("800");
		 Element table=getTable();
		 String tableHtml=(new XMLOutputter()).outputString(table);
		 if(this.table.getThead()==null){
			 tableHtml=tableHtml.replace("<thead />",this.table.getTheadHtml());
		 }
		 StringBuffer sb=new StringBuffer("<html xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:x=\"urn:schemas-microsoft-com:office:excel\" xmlns=\"http://www.w3.org/TR/REC-html40\"> <head></head><body>");
		 sb.append(this.getReportName());
		 if(this.needFormat.equals(ReportConstant.REPORTRPCOLUMN_FORMAT_YES)){
			 sb.append(this.getTableHeader());
		 }
		 sb.append(tableHtml);
		 if(this.needFormat.equals(ReportConstant.REPORTRPCOLUMN_FORMAT_YES)){
			 sb.append(this.getTableFooter());
		 }
		 sb.append("</body>");
		 
		 return sb.toString().replace("x:str=\"\"","x:str").replace("x:num=\"\"","x:num");
	} 
	
	public String getPDFReport(){
	 	 this.table.setAlign("center");
	 	 this.table.setBorder("1");
	 	 this.table.setWidth("600");
		 Element table=getTable();
		 String tableHtml=(new XMLOutputter()).outputString(table);
		 if(this.table.getThead()==null){
			 tableHtml=tableHtml.replace("<thead />",this.table.getTheadHtml());
		 }
		 StringBuffer sb=new StringBuffer("<html><head></head><body>");
		 sb.append(this.getReportName());
		 if(this.needFormat.equals(ReportConstant.REPORTRPCOLUMN_FORMAT_YES)){
			 sb.append(this.getTableHeader());
		 }
		 sb.append(tableHtml);
		 if(this.needFormat.equals(ReportConstant.REPORTRPCOLUMN_FORMAT_YES)){
			 sb.append(this.getTableFooter());
		 }
		 sb.append("</body>");
		 return sb.toString();
	} 
		
	private Element getTable(){
		Element table=new Element("table");
		
		if(this.table.getClassName()!=null&&!this.table.getClassName().equals("")){
			table.setAttribute("class",this.table.getClassName());
		}
		if(this.table.getAlign()!=null&&!this.table.getAlign().equals("")){
			table.setAttribute("align",this.table.getAlign());
		}
		if(this.table.getWidth()!=null&&!this.table.getWidth().equals("")){
			table.setAttribute("width",this.table.getWidth());
		}
		if(this.table.getBorder()!=null&&!this.table.getBorder().equals("")){
			table.setAttribute("border",this.table.getBorder());
		}
		//table.setAttribute("x:str","");
		
		table.setAttribute("str","",nsX);
		Element thead=new Element("thead");
		if(this.table.getThead()!=null){
			for(Tr tr:this.table.getThead().getTrs()){
				Element trHtml=new Element("tr");
				thead.addContent(trHtml);
				for(Td td:tr.getTds()){
					if(td==null)continue;
					Element tdHtml=new Element("td");
					trHtml.addContent(tdHtml);
					
					Element font=new Element("font");
					tdHtml.setContent(font);
					font.setAttribute("color",td.getFontcolor()==null?"#000000":td.getFontcolor());
					font.setAttribute("size",td.getFontsize());
					font.setText(td.getText()==null?" ":td.getText());

					if(td.getBgcolor()!=null&&!td.getBgcolor().trim().equals("")){
						tdHtml.setAttribute("bgcolor",td.getBgcolor());
					}
					if(td.getAlign()!=null&&!td.getAlign().trim().equals("")){
						tdHtml.setAttribute("align",td.getAlign());
					}
					if(td.getValign()!=null&&!td.getValign().trim().equals("")){
						tdHtml.setAttribute("valign",td.getValign());
					}
					if(td.getColspan()!=0){
						tdHtml.setAttribute("colspan",""+td.getColspan());
					}
					if(td.getRowspan()!=0){
						tdHtml.setAttribute("rowspan",""+td.getRowspan());
					}

					tdHtml.setAttribute("nowrap","");
				}
			}
		}
		table.addContent(thead);
		
		Element tbody=new Element("tbody");
		for(Tr tr:this.table.getTbody().getTrs()){
			Element trHtml=new Element("tr");
			tbody.addContent(trHtml);
			for(Td td:tr.getTds()){
				if(td==null)continue;
				Element tdHtml=new Element("td");
				trHtml.addContent(tdHtml);		
				Element font=new Element("font");
				tdHtml.setContent(font);
				font.setAttribute("color",td.getFontcolor()==null?"#FFFFFF":td.getFontcolor());
				font.setAttribute("size",td.getFontsize());
				font.setText(td.getText()==null?" ":td.getText());
				if(td.getBgcolor()!=null&&!td.getBgcolor().trim().equals("")){
					tdHtml.setAttribute("bgcolor",td.getBgcolor());
				}
				if(td.getAlign()!=null&&!td.getAlign().trim().equals("")){
					tdHtml.setAttribute("align",td.getAlign());
					if(td.getAlign().equals(ReportConstant.ALIGN_RIGHT)){
						tdHtml.setAttribute("str","",nsX);
					}else{
						tdHtml.setAttribute("str","",nsX);
					}
				}
				if(td.getValign()!=null&&!td.getValign().trim().equals("")){
					tdHtml.setAttribute("valign",td.getValign());
				}
				if(td.getColspan()!=0){
					tdHtml.setAttribute("colspan",""+td.getColspan());
				}
				if(td.getRowspan()!=0){
					tdHtml.setAttribute("rowspan",""+td.getRowspan());
				}

				tdHtml.setAttribute("nowrap","");

			}
		}
		table.addContent(tbody);

		return table;
	}
	
	private String getReportName(){
		return 
		"<table border=\"0\" align=\"center\" width=\""+this.table.getWidth()+"\"><tr >" +	
		"<td align=\"center\" colspan=\""+this.table.getTbody().getTrs().get(0).getTds().length+"\">" +
		"<font size=\"4\"  ><b>"+this.reportName+"</b></font><br/><br/>" +
		"</td>" + 
		"</tr></table>";
		
	}
	
	private String getTableHeader(){
		return "<table border=\"0\" align=\"center\" width=\""+this.table.getWidth()+"\"><tr>" +
				"<td align=\"left\" ><font size=\"2\"  >金额单位:元</font></td>"+
				"<td align=\"right\" colspan=\""+ (this.table.getTbody().getTrs().get(0).getTds().length-1) +"\">" +
				"<b><font size=\"2\"  >"+this.parametersStr+"</font></b></td>" +
				"</tr></table>";
	}
	
	private String getTableFooter(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String modeDate = sdf.format(new Date());
		int cells = this.table.getTbody().getTrs().get(0).getTds().length;
		int tdCells = cells / 3;
		String[][] footTitle = new String[2][3];
		footTitle[0][0] = "制表人:";
		footTitle[0][1] = "审核:";
		footTitle[0][2] = "复核:";
		footTitle[1][0] = "分管领导:";
		footTitle[1][1] = "总经理:";
		footTitle[1][2] = "制表日期:";
		StringBuffer sb = new StringBuffer();
		sb.append("<br/><table border=\"0\" cellpadding=10 align=\"center\" width=\"" + this.table.getWidth() + "\">");
		for (int i = 0; i < 2; i++)
		{
			sb.append("<tr>");
			for (int j = 0; j < 3; j++)
			{
				sb.append("<td align=\"left\"><font size=\"2\" >" + footTitle[i][j] + "</font></td>");
				if(tdCells >= 1){
					for (int x = 0; x <= tdCells - 1; x++)
					{
						if (x == 0 && i == 1 && j == 2)
						{
							sb.append("<td align=\"left\"><font size=\"2\" >" + modeDate + "</font></td>");
						}
						else
						{
							sb.append("<td>&nbsp;</td>");
						}
					}
				}else{
					if (i == 1 && j == 2)
					{
						sb.append("<td align=\"left\"><font size=\"2\" >" + modeDate + "</font></td>");
					}
				}
				
			}
			sb.append("</tr>");
		}
		sb.append("</table>");
		return sb.toString();
//		return "<br/><table border=\"0\" cellpadding=10 align=\"center\" width=\""+this.table.getWidth()+"\">" +
//				"<tr>" +
//					"<td  align=\"left\" ><font size=\"2\" >制表人：</font></td>" +
//					"<td  width=\"200\"></td>" +
//					"<td  width=\"200\"></td>" +
//					"<td  width=\"150\" align=\"left\" ><font size=\"2\" >审核人：</font></td>" +
//					"<td  width=\"200\"></td>" +
//					"<td  width=\"150\" align=\"left\" ><font size=\"2\"  >复核人：</font></td>" +
//					"<td  width=\"200\"></td>" +
//				"</tr>" +
//				"<tr>" +
//					"<td   colspan=4 >&nbsp;</td>" +
//				"</tr>" +
//				"<tr>" +
//					"<td  align=\"left\" ><font size=\"2\" >分管领导 ：</font></td>" +
//					"<td  width=\"200\"></td>" +
//					"<td  width=\"150\"  align=\"left\" ><font size=\"2\"  >总经理：</font></td>" +
//					"<td  width=\"200\"></td>" +
//					"<td  width=\"150\" align=\"left\" ><font size=\"2\"  >制表日期：</font></td>"  +
//					"<td width=\"150\" align=\"left\"><font size=\"2\" >" + modeDate + "</font></td>"+
//				"</tr>" +
//				"</table>";
	}
	

	 
}
