package jieyi.app.taglib;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import jieyi.app.util.DictHelper;
import jieyi.app.util.PageData;
/**
 * Tag for creating multiple &lt;select&gt; options for displaying a list of
 * country names.
 * 
 * <p>
 * <b>NOTE</b> - This tag requires a Java2 (JDK 1.2 or later) platform.
 * </p>
 * DEMO:
 * select 标签
 * <ht:select name="name" type="select" typeName="字典名" cssClass="指定样式表" value="当前值" withBlankOption="true|1|false|0"></ht:select>
 * translate 标签
 * <ht:select type="translate" typeName="字典名" key="字典项编码"></ht:select>
 * 
 * @author lacet
 * @version 1.0
 * @Date 时间
 * @copyright jieyi.com
 */
public class SelectTag extends TagSupport {

	public static final String SELECT_TAG_TYPE_SELECT = "select";
	public static final String SELECT_TAG_TYPE_RADIO = "radio";
	public static final String SELECT_TAG_TYPE_CHECKBOX = "checkbox";
	public static final String SELECT_TAG_TYPE_TRANSLATE = "translate";
	/**
	 * 
	 */
	private static final long serialVersionUID = 8360586906345090691L;

	private String type;

	private String id;

	private String name;

	private String typeName;

	private String cssClass;

	private String defaultValue;

	private String tabIndex;

	private String disabled;

	private String onchange;

	private String withBlankOption;

	private String multiple;

	private String size;
	
	private String key;
	
	private String value;

	private String style;
	
	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		if(value != null && !"".equals(value)){
			value = value.trim();
		}
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		if(key != null && !"".equals(key)){
			key = key.trim();
		}
		this.key = key;
	}

	/**
	 * @param multiple
	 *            the multiple to set
	 */
	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(String size) {
		this.size = size;
	}

	/**
	 * @param withBlankOption
	 *            the withBlankOption to set
	 */
	public void setWithBlankOption(String withBlankOption) {
		this.withBlankOption = withBlankOption;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param name
	 *            The name to set.
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setName(String name) {
		this.name = name;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	/**
	 * @param selected
	 *            The selected option.
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * Set the value of tabIndex
	 * 
	 * @param tabIndex
	 *            to set
	 */
	public void setTabIndex(String tabIndex) {
		this.tabIndex = tabIndex;
	}

	/**
	 * @param typeName
	 *            the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * @param disabled
	 *            the disabled to set
	 */
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	/**
	 * @param onchange
	 *            the onchange to set
	 */
	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	/**
	 * Process the start of this tag.
	 * 
	 * @return int status
	 * 
	 * @exception JspException
	 *                if a JSP exception has occurred
	 * 
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	public int doStartTag() throws JspException {
		try {
			pageContext.getOut().write(build());
		} catch (IOException io) {
			throw new JspException(io);
		}
		return super.doStartTag();
	}

	/**
	 * Release aquired resources to enable tag reusage.
	 * 
	 * @see javax.servlet.jsp.tagext.Tag#release()
	 */
	public void release() {
		super.release();
	}
	
	
	private String build(){
		if(type != null &&( type.equals(SELECT_TAG_TYPE_RADIO) || type.equals(SELECT_TAG_TYPE_CHECKBOX))){
			return buildCheck();
		}else if(type.equals(SELECT_TAG_TYPE_TRANSLATE)){
			return buildTranslate();
		}else{
			return buildSelect();
		}
	}
	
	protected String buildTranslate(){
		if (typeName != null && !typeName.equals("") && key != null && !key.equals("")) {
			return DictHelper.translateDictValue(typeName, key.trim());
		}else{
			return key;
		}
	}
	
	protected String buildSelect(){
		List<PageData> items = this.buildItemList();
		StringBuffer sb = new StringBuffer();
		sb.append("<select type='width:50px;' ");
		if (name != null && !name.equals("")) {
			sb.append(" name=\"" + name + "\"");
		}
		if (id != null && !id.equals("")) {
			sb.append(" id=\"" + id + "\"");
		}
		if (cssClass != null && !cssClass.equals("")) {
			sb.append(" class=\"" + cssClass + "\"");
		}
		if (tabIndex != null && !tabIndex.equals("")) {
			sb.append(" tabIndex=\"" + tabIndex + "\"");
		}
		if (disabled != null && !disabled.equals("")) {
			sb.append(" disabled=\"" + disabled + "\"");
		}
		if (onchange != null && !onchange.equals("")) {
			sb.append(" onchange=\"" + onchange + "\"");
		}
		if (multiple != null && !multiple.equals("")) {
			sb.append(" multiple=\"" + multiple + "\"");
		}
		if (size != null && !size.equals("")) {
			sb.append(" size=\"" + size + "\"");
		}
		if (style != null && !style.equals("")) {
			sb.append(" style=\"" + style + "\"");
		}
		sb.append(">\n");

		if (withBlankOption != null && withBlankOption.equals("true")) {
			sb.append("    <option value=\"\">---所有---</option>\n");
		} else if (withBlankOption != null && withBlankOption.equals("1")) {
			sb.append("    <option value=\"\"></option>\n");
		} else if(withBlankOption != null && withBlankOption.equals("0")) {
			sb.append("    <option value=\"\">--请选择--</option>\n");
		}
		if(items != null && items.size() > 0){
			for (Iterator<PageData> i = items.iterator(); i.hasNext();) {
				PageData dict = i.next();
				sb.append("    <option value=\"" + dict.get("dictvalue") + "\"");
				if(value!=null && value.equals(dict.get("dictvalue"))){
					sb.append(" selected=\"selected\"");
				}else if (defaultValue!=null && defaultValue.equals(dict.get("dictvalue"))){
					sb.append(" selected=\"selected\"");
				}else if ((withBlankOption == null || withBlankOption.equals("0")|| withBlankOption.equals("false")) && value==null) {
					sb.append(" selected=\"selected\"");
				}
				sb.append(">" + dict.get("dictvaluedesc") + "</option>\n");
	
			}
		}

		sb.append("</select>");
		return sb.toString();
	}

	protected String buildCheck(){
		List<PageData> items = this.buildItemList();
	//	String dictName  = DictHelper.getDict(this.typeName).getName();
		StringBuffer sb = new StringBuffer();
		for (Iterator<PageData> i = items.iterator(); i.hasNext();) {
			PageData dict = i.next();
			String cname = (String) dict.get("dictvaluedesc");
			String value = (String) dict.get("dictvalue");
			String selected = "";
			
			sb.append("<label ").append(cname).append(">");
			sb.append("<input type=\""+type+"\" name=\""+ name +"\" value=\""+value+"\" ");
			sb.append(selected).append(" ");
			sb.append("/>" + value);
			sb.append("</label>");
			if (withBlankOption != null && withBlankOption.equals("true")){
				sb.append("<br>");
			}
		}
		
		return sb.toString();
	}
	

	/**
	 * Build a List of LabelValues for all the available countries. Uses the two
	 * letter uppercase ISO name of the country as the value and the localized
	 * country name as the label.
	 * 
	 * @param locale
	 *            The Locale used to localize the country names.
	 * 
	 * @return List of LabelValues for all available countries.
	 */
	public List<PageData> buildItemList() {
		List<PageData> dicts = DictHelper.getDictValues(this.typeName);
		return dicts;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
