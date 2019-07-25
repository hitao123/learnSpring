package com.example.learn.db.dao;

import com.example.learn.db.domain.MallAd;
import com.example.learn.db.domain.MallAdExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MallAdMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mall_ad
     *
     * @mbg.generated
     */
    long countByExample(MallAdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mall_ad
     *
     * @mbg.generated
     */
    int deleteByExample(MallAdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mall_ad
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mall_ad
     *
     * @mbg.generated
     */
    int insert(MallAd record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mall_ad
     *
     * @mbg.generated
     */
    int insertSelective(MallAd record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mall_ad
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    MallAd selectOneByExample(MallAdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mall_ad
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    MallAd selectOneByExampleSelective(@Param("example") MallAdExample example, @Param("selective") MallAd.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mall_ad
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<MallAd> selectByExampleSelective(@Param("example") MallAdExample example, @Param("selective") MallAd.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mall_ad
     *
     * @mbg.generated
     */
    List<MallAd> selectByExample(MallAdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mall_ad
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    MallAd selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") MallAd.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mall_ad
     *
     * @mbg.generated
     */
    MallAd selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mall_ad
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    MallAd selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mall_ad
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") MallAd record, @Param("example") MallAdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mall_ad
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") MallAd record, @Param("example") MallAdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mall_ad
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(MallAd record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mall_ad
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(MallAd record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mall_ad
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByExample(@Param("example") MallAdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mall_ad
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByPrimaryKey(Integer id);
}