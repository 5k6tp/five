package com.fivecode.fiveadmin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = true)
public class SysRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "角色名称不能为空")
    private String name;

    @NotBlank(message = "角色编码不能为空")
    private String code;

    /**
     * 备注
     */
    private String remark;

    @TableField(exist = false)
    private List<Long> menuIds = new ArrayList<>();

}
