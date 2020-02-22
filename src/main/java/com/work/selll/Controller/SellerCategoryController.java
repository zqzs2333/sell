package com.work.selll.Controller;

import com.work.selll.Util.ResultVoUtil;
import com.work.selll.VO.ResultVo;
import com.work.selll.bean.productCategory;
import com.work.selll.exception.SellException;
import com.work.selll.form.CategoryForm;
import com.work.selll.service.categoryService;
import com.work.selll.service.impl.CategoryServiceImpl;
import enums.ResultEnums;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/seller/category/")
public class SellerCategoryController {
    @Autowired
    categoryService categoryService;
    @GetMapping("/list")
    public ResultVo list()
    {
        List<productCategory> productCategoryList = categoryService.findAll();
        return ResultVoUtil.success(productCategoryList);

    }
    @GetMapping("/find")
    public ResultVo findone(@RequestParam("categoryId") Integer categoryId)
    {
        if (categoryId == null)
        {
            return ResultVoUtil.fail(100,"categoryId错误");
        }
        productCategory result = categoryService.findOne(categoryId);
        return ResultVoUtil.success(result);

    }

    @PostMapping("/motify")
    public ResultVo add(@Valid CategoryForm categoryForm, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            throw new SellException(ResultEnums.param_error.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        productCategory productCategory = categoryService.findOne(categoryForm.getCategoryId());
        BeanUtils.copyProperties(categoryForm,productCategory);
        categoryService.save(productCategory);
        return ResultVoUtil.success();

    }
}
