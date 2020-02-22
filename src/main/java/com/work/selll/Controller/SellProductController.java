package com.work.selll.Controller;

import com.work.selll.DTO.OrderDTO;
import com.work.selll.Util.KeyUtil;
import com.work.selll.Util.ResultVoUtil;
import com.work.selll.VO.ResultVo;
import com.work.selll.bean.ProductInfo;
import com.work.selll.bean.productCategory;
import com.work.selll.exception.SellException;
import com.work.selll.form.ProductForm;
import com.work.selll.service.ProductService;
import com.work.selll.service.categoryService;
import com.work.selll.service.impl.CategoryServiceImpl;
import enums.ResultEnums;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("seller/product")
public class SellProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private categoryService categoryService;
    @GetMapping("list")
    public ResultVo list(@RequestParam(value = "page" ,defaultValue = "1") Integer page,
                         @RequestParam(value = "size",defaultValue = "10") Integer size,
                         Map<String,Object> map)
    {

        PageRequest of = PageRequest.of(page-1, size);

        Page<ProductInfo> productInfoPage = productService.findAll(of);
        return ResultVoUtil.success(productInfoPage.getContent());

    }
    @GetMapping("onSale")
    public ResultVo onSale(@RequestParam("productId") String productId)
    {
        ProductInfo productInfo = productService.findOne(productId);
        if (productInfo==null) {
            throw new SellException(ResultEnums.product_not_exist);
        }
        productService.onSale(productId);
        return ResultVoUtil.success();
    }

    @GetMapping("offSale")
    public ResultVo offSale(@RequestParam("productId") String productId)
    {
        ProductInfo productInfo = productService.findOne(productId);
        if (productInfo==null) {
            throw new SellException(ResultEnums.product_not_exist);
        }
        productService.offSale(productId);
        return ResultVoUtil.success();
    }
    @GetMapping("/addAndMotify/show")
    public ResultVo show(@RequestParam(value = "productId",required = false) String productId)
    {
        if (!StringUtils.isEmpty(productId)) {
            ProductInfo productInfo = productService.findOne(productId);
            return ResultVoUtil.success(productInfo);
        }
        List<productCategory> productCategoryList = categoryService.findAll();
        return ResultVoUtil.success(productCategoryList);

    }
    @PostMapping("/addAndMotify/save")
     public ResultVo save(@Valid ProductForm productForm, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            throw new SellException(ResultEnums.param_error.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        ProductInfo productInfo = new ProductInfo();
        if (!StringUtils.isEmpty(productForm.getProductId()))
        {
            productInfo = productService.findOne(productForm.getProductId());
        }else {
            productForm.setProductId(KeyUtil.getUniqueKey());
        }


        BeanUtils.copyProperties(productForm,productForm);
        productService.save(productInfo);
        return ResultVoUtil.success();

    }
}
