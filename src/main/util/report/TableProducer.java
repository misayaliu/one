package jieyi.app.util.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jieyi.app.form.report.t_report_base;
import jieyi.app.form.report.t_report_column;
public class TableProducer {
	private String[][] data;
	private t_report_base reportRpBase;
	private List<t_report_column> reportRpColumns;

	private Table table;

	
	public Table getTable(List<t_report_column> reportRpColumns,t_report_base reportRpBase,String[][] data) throws Exception{
		this.data=data;
		this.reportRpBase=reportRpBase;
		this.reportRpColumns=reportRpColumns;
		table= new Table();
		this.createThead();
		this.createTbody();
		return table;
	}
	
	/*
	 * 生成表头
	 */
	private void createThead(){
		if(reportRpBase.getNeed_header().equals(ReportConstant.REPORTRPBASE_NEEDHEADER_YES)){ //通过HTML生产表头
			table.setTheadHtml(reportRpBase.getHeader());
		}else{ //自制表头
			Tr tr=new Tr(reportRpColumns.size());
			int i=0;
			for(t_report_column reportRpColumn:reportRpColumns){
				Td td=new Td();
				td.setText(reportRpColumn.getColumn_name());
				td.setAlign(ReportConstant.THEAD_TD_DEFAUL_ALIGN);
				td.setFontsize(ReportConstant.THEAD_TD_DEFAUL_FONT_SIZE);
				tr.getTds()[i++]=td;
			}
			Thead thead=new Thead();
			List<Tr> trs=new ArrayList<Tr>();
			trs.add(tr);
			thead.setTrs(trs);
			table.setThead(thead);
		}
	}
	
	/*
	 * 生成表体
	 */
	private void createTbody() throws Exception{
		Tbody tbody=new Tbody();
		List<Tr> list=new ArrayList<Tr>();
		Map<Integer,String> map=new HashMap();
		Map<Integer,Integer> map_index=new HashMap();
		for(int i=0;i<data[0].length;i++){//先判断是否需要有序号列
			if(!reportRpColumns.get(i).getRollup_type().equals("1")){
				if(reportRpColumns.get(i).getRollup_type().equals(ReportConstant.REPORTRPCOLUMN_ROLLUPTYPE_XUHAO)){
					map.put(i, reportRpColumns.get(i).getColumn_name());
					map_index.put(i,1);
				}
			}
		}
		//1.填充数据
	 	for(String[] trStr:data){
			Tr tr=new Tr(trStr.length);
			for(int i=0;i<trStr.length;i++){
				Td td=new Td();
				if(trStr[i]==null){
					if(!reportRpColumns.get(i).getRollup_type().equals("1")){
						if(reportRpColumns.get(i).getRollup_type().equals(ReportConstant.REPORTRPCOLUMN_ROLLUPTYPE_HEJI)){
							td.setText("合计");
						}else if(reportRpColumns.get(i).getRollup_type().equals(ReportConstant.REPORTRPCOLUMN_ROLLUPTYPE_XIAOJI)){
							if(i>=1){
								String text = tr.getTds()[i-1].getText();//看上一行是不是也是"小计："
								if(text!=null&&text.equals("小计")){
									td.setText(" ");
								}else{
									td.setText("小计");
								}
							}else{
								td.setText("小计");
							}
						}
						td.setIsTotal(true);
					}
				}else{
					td.setText(trStr[i]);
				}
				td.setFontsize(ReportConstant.TBODY_TD_DEFAUL_FONT_SIZE);
				td.setFontcolor(reportRpColumns.get(i).getFont_color());
				td.setBgcolor(reportRpColumns.get(i).getBg_color());
				String align = reportRpColumns.get(i).getAlign();
				if(align.equals("1")){
					td.setAlign("left");
				}else if(align.equals("2")){
					td.setAlign("center");
				}else if(align.equals("3")){
					td.setAlign("right");
				}
				tr.getTds()[i]=td;
			}
			for(int i=0;i<trStr.length;i++){
				String temp=map.get(i);
				if(temp!=null){
					Td td=tr.getTds()[Integer.parseInt(temp)];
					int index=map_index.get(i);
					if(td.getText().equals("小计")||td.getText().equals("总计")||td.getText().equals("null")||td.getText().equals(" ")){
						String text = tr.getTds()[Integer.parseInt(temp)-1].getText();//看上一行是不是也是"小计："
						if(text.equals("小计")||text.equals("总计")||text.equals("null")||text.equals(" ")){
							map_index.put(i,1);
							tr.getTds()[i].setText(" ");
						}else{
							tr.getTds()[i].setText(index+"");
							map_index.put(i, index+1);
						}
					}else{
						String text = tr.getTds()[Integer.parseInt(temp)-1].getText();//看上一行是不是空
						if(text.equals("小计")||text.equals("总计")||text.equals("null")||text.equals(" ")){
							System.out.println(text);
							tr.getTds()[i].setText(" ");
						}else{
							tr.getTds()[i].setText(index+"");
						}
					}
				}
			}
			list.add(tr);
		}
		
		
		if(!hasRollup()){ //2.当sql中无rollup时  合并列中相同的数据
			int j=-1;
			for(t_report_column reportRpColumn:reportRpColumns){
				j++;
				if(reportRpColumn.getMerged().equals(ReportConstant.REPORTRPCOLUMN_TMERGED_NO))continue;//不合并
				String lastHtml=null;
				int lastNum=0;
				for(int i=0;i<data.length;i++){
					if(lastHtml==null){
						lastHtml=list.get(i).getTds()[j].getText();
						lastNum=i;
						continue;
					}
					String nowHtml=list.get(i).getTds()[j].getText();
					if(nowHtml==null){
						lastHtml=null;
						lastNum=i;
						continue;
					}
					if(lastHtml.equals(nowHtml)){
						if(isRowTmerged(i,j,list)){
							list.get(i).getTds()[j].setRowspan(-1);
							list.get(lastNum).getTds()[j].setRowspan(i-lastNum+1);
						}else{
							lastHtml=nowHtml;
							lastNum=i;
						}
					}else{
						lastHtml=nowHtml;
						lastNum=i;
					}
				}
			}
			j=-1;
			for(t_report_column reportRpColumn:reportRpColumns){
				j++;
				if(reportRpColumn.getMerged().equals(ReportConstant.REPORTRPCOLUMN_TMERGED_NO))continue;//不合并
				for(int i=0;i<data.length;i++){
					if(list.get(i).getTds()[j].getRowspan()==-1)list.get(i).getTds()[j]=null;
				}
			}
			tbody.setTrs(list);
			table.setTbody(tbody);
		}else{ //2.当sql中有rollup时  合并列中相同的数据
			int j=-1;
			for(t_report_column reportRpColumn:reportRpColumns){
				j++;
				if(reportRpColumn.getMerged().equals(ReportConstant.REPORTRPCOLUMN_TMERGED_NO))continue;//不合并
				String lastHtml=null;
				int lastNum=0;
				for(int i=0;i<data.length;i++){
					if(list.get(i).getTds()[j].getIsTotal()){
						list.get(lastNum).getTds()[j].setRowspan(i-lastNum);
						lastHtml=null;
						lastNum=i+1;
						int firstTotal=getFirstTotal(list.get(i),j);
						if(firstTotal!=-1){
							list.get(i).getTds()[firstTotal].setColspan(j-firstTotal+1);
							list.get(i).getTds()[j]=null;
						}else{
							setMergedFontTdColor(i,j,list);
						}
						continue;
					}
					if(lastHtml==null){
						lastHtml=list.get(i).getTds()[j].getText();
						lastNum=i;
						continue;
					}
					String nowHtml=list.get(i).getTds()[j].getText();
					if(nowHtml==null){
						lastHtml=null;
						lastNum=i;
						continue;
					}
					if(lastHtml.equals(nowHtml)){
						list.get(i).getTds()[j]=null;
					}else{
						list.get(lastNum).getTds()[j].setRowspan(i-lastNum);
						lastHtml=nowHtml;
						lastNum=i;
					}
				}
			}
			tbody.setTrs(list);
			table.setTbody(tbody);
		}
		Tr tr=table.getTbody().getTrs().get(table.getTbody().getTrs().size()-1);
		int num=0;
		for(Td td:tr.getTds()){
			t_report_column reportRpColumn=reportRpColumns.get(num++);
			if("2".equals(reportRpColumn.getIs_last_row_show())){
				td.setText(" ");
			}
		}
	}
	
	private boolean hasRollup(){
		for(t_report_column reportRpColumn:reportRpColumns){
			if(!reportRpColumn.getRollup_type().equals("1"))return true;
		}
		return false;
	}
	
	/*
	 * 纵向合并
	 */
	private boolean isRowTmerged(int row,int col,List<Tr> list){
		for(int c=0;c<col;c++){
			if(!list.get(row).getTds()[c].getText().equals(list.get(row-1).getTds()[c].getText()))return false;
		}
		return true;
	}
	
	/*
	 * 横行合并
	 */
	private int getFirstTotal(Tr tr,int num){
		for(int i=0;i<num;i++){
			if(tr.getTds()[i]==null)continue;
			if(tr.getTds()[i].getIsTotal())return i;
		}
		return -1;
	}
	
	/*
	 * 设置合计字体颜色以及合计背景颜色
	 */
	private void setMergedFontTdColor(int row,int col,List<Tr> list){
		int length=list.get(0).getTds().length;
		for(int c=col;c<length;c++){
			list.get(row).getTds()[c].setBgcolor(reportRpColumns.get(col).getMerged_bg_color());
			list.get(row).getTds()[c].setFontcolor(reportRpColumns.get(col).getMerged_font_color());
		}
	}
}
