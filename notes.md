need to have a standard representation for dataset.  I'm thinking column-wise is better than row-wise so that I don't have to keep buggering around with every transformation.

probably stick it all in a record.  although I suppose a map could work too, since it's not like I need protocols. 

I suppose the other alternative, perhaps easier, is to define protocols for all the transformations and then have distinct records for both row-wise and column-wise.  That might be a little overkill/yangi though. 

I guess the easiest thing would be multimethods. Just define datasets as a map with nested vectors and also keys: 
:column-wise (bool)
:headers (vec or nil)

It might also be useful to define column vectors as either numeric or non-numeric.  (But really, why bother?  why not just have the maps describe column labels.) 

Actually, if I use records and protocols then I can have things like log-transform defined with respect to an entire dataset (just picking numeric vectors) and/or individual columns.  

need to define simple transformations:
- standardize
- log-transform (just map js.Math.log)
- boolean or one-hot transforms for binary variable columns?
