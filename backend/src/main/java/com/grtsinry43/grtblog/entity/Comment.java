package com.grtsinry43.grtblog.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author grtsinry43
 * @since 2024-10-09
 */
@Data
public class Comment implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 评论 ID，会由雪花算法生成
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 评论区 id
     */
    private Long areaId;

    /**
     * 评论内容（markdown 格式）
     */
    private String content;

    /**
     * 评论者 ID
     */
    private Long authorId;

    /**
     * 评论者昵称
     */
    private String nickName;

    /**
     * 评论者 IP 地址
     */
    private String ip;

    /**
     * 评论者归属地
     */
    private String location;

    /**
     * 评论者操作系统
     */
    private String platform;

    /**
     * 评论者浏览器
     */
    private String browser;

    /**
     * 评论者邮箱
     */
    private String email;

    /**
     * 评论者网站
     */
    private String website;

    /**
     * 评论是否查看过
     */
    private Boolean isViewed;

    /**
     * 置顶
     */
    private Boolean isTop;

    @TableField(value = "created_at", insertStrategy = FieldStrategy.NEVER, updateStrategy = FieldStrategy.NEVER)
    private LocalDateTime createdAt;
    @TableField(value = "updated_at", insertStrategy = FieldStrategy.NEVER, updateStrategy = FieldStrategy.NEVER)
    private LocalDateTime updatedAt;

    /**
     * 评论者是否是博主
     */
    private Boolean isOwner;

    /**
     * 评论者是否是作者
     */
    private Boolean isAuthor;

    /**
     * 评论者是否是好友
     */
    private Boolean isFriend;

    /**
     * 评论删除时间（软删除），如果不为空则表示已删除
     */
    private LocalDateTime deletedAt;

    /**
     * 父评论 ID，如果为空则表示是顶级评论，否则是回复评论
     */
    private Long parentId;
}
