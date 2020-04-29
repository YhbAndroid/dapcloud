package com.dap.cloud.base.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dap.cloud.constants.StringConstant;
import com.dap.cloud.utils.StringUtils;
import com.sun.deploy.util.OrderedHashSet;
import org.apache.poi.hssf.record.formula.functions.T;


/**
 * @author cuichen
 */
public class TreeJson implements Serializable {
    private static final long serialVersionUID = 1L;

    public String id;
    /**selectTree*/
    private String name;
    private String icon;
    private String parentId;
    private String url;
    private boolean open;
    private boolean checked;
    /**tree*/
    private String title;
    private boolean spread;

    private List<TreeJson> children = new ArrayList<TreeJson>();

    public boolean isSpread() {
        return spread;
    }

    public void setSpread(boolean spread) {
        this.spread = spread;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<TreeJson> getChildren() {
        return children;
    }

    public void setChildren(List<TreeJson> children) {
        this.children = children;
    }

    public static void getTree(List<TreeJson> list, TreeJson root) {
        for (int i = 0; i < list.size(); i++) {
            TreeJson tj = list.get(i);
            if (StringUtils.isNotBlank(tj.getParentId()) && tj.getParentId().equals(root.getId())) {
                if (root.getChildren().indexOf(tj) == -1) {
                    root.getChildren().add(tj);
                }
                list.remove(tj);
                i--;
            }
        }
        if (root.getChildren().size() > 0) {
            for (int i = 0; i < root.getChildren().size(); i++) {
                getTree(list, root.getChildren().get(i));
            }
        } else {
            root.setChildren(null);
        }

    }

}
